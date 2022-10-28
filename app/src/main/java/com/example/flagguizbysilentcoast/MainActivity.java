package com.example.flagguizbysilentcoast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagguizbysilentcoast.Classes.AnswersRecyclerAdapter;
import com.example.flagguizbysilentcoast.Classes.Flag;
import com.example.flagguizbysilentcoast.Fragments.Fragment4Answers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AnswersRecyclerAdapter adapter;
    FrameLayout frameLayoutAnswers;
    ImageButton btnSettings;
    ImageButton btnGiveHint;
    ImageButton btnStartPlay;
    ImageView imgQuestion;
    TextView txtQuestionNumber;
    int questionNumber;
    List<Flag> flags = new ArrayList<>();
    Flag currentFlag;
    List<String> answers;
    SharedPreferences prefs;
    int numberOfCorrectAnswers;
    int numberOfWrongAnswers;
    MediaPlayer mediaPlayer;
    boolean isHintOpen;
    ImageView imgHint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        answers= Arrays.asList(getResources().getStringArray(R.array.Countries));
        //
        String thtext="";
        for (String i:answers
             ) {
            thtext+=i;
        }
        Log.d("gg",thtext);
        //
        imgQuestion = findViewById(R.id.imgQuestion);
        txtQuestionNumber = findViewById(R.id.txtQuestionNumber);
        imgHint = findViewById(R.id.imgHint);
        btnStartPlay = findViewById(R.id.btnStartPlay);

        btnStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionNumber = 1;
                numberOfCorrectAnswers = 0;
                numberOfWrongAnswers = 0;
                UpdateTxtNumberOfQuestion();

                if(Objects.equals(prefs.getString("contentOfQuiz", ""), "Whole world")||Objects.equals(prefs.getString("contentOfQuiz", ""), "Весь мир")){
                    flags.add(new Flag(answers.get(0), R.drawable.romania));
                    flags.add(new Flag(answers.get(1)   ,R.drawable.australia));
                    flags.add(new Flag(answers.get(2)     ,R.drawable.sweden));
                    flags.add(new Flag(answers.get(3)     ,R.drawable.argentina));
                    flags.add(new Flag(answers.get(4)   ,R.drawable.brazil));
                    flags.add(new Flag(answers.get(5)    ,R.drawable.cuba));
                    flags.add(new Flag(answers.get(6)    ,R.drawable.czech_republic));
                    flags.add(new Flag(answers.get(7)         ,R.drawable.norway));
                    flags.add(new Flag(answers.get(8)         ,R.drawable.switzerland));
                    flags.add(new Flag(answers.get(9)        ,R.drawable.ukraine));
                }

                int r = (int)(Math.random() * ((flags.size()-1) + 1));
                currentFlag = flags.get(r);
                flags.remove(r);





                if( prefs.getString("numberOfAnswers","").equals("4")){

                    frameLayoutAnswers = findViewById(R.id.frameLayoutAnswers);
                    Fragment4Answers fragment = new Fragment4Answers(answers,currentFlag.getName());
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(frameLayoutAnswers.getId(),fragment)
                            .commit();
                }

            }
        });

        isHintOpen = false;
        btnGiveHint = findViewById(R.id.btnGiveHint);
        btnGiveHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHintOpen = !isHintOpen;
                if(isHintOpen){
                    btnGiveHint.setColorFilter(getResources().getColor( R.color.red));
                    imgHint.setVisibility(View.VISIBLE);
                    if(currentFlag.getName().equals("Romania")||currentFlag.getName().equals("Румыния")){
                        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.andrew_tate_audio);
                        mediaPlayer.start();
                        imgHint.setImageResource(R.drawable.andrew_tate);

                    }
                    else if(currentFlag.getName().equals("Argentina")||currentFlag.getName().equals("Аргентина")){
                        imgHint.setImageResource(R.drawable.messi);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                R.string.hints_are_not_available,
                                Toast.LENGTH_SHORT)
                        .show();
                        imgHint.setVisibility(View.INVISIBLE);
                        btnGiveHint.setColorFilter(getResources().getColor(R.color.black));
                    }
                }
                else{
                    btnGiveHint.setColorFilter(getResources().getColor( R.color.black));
                    if(mediaPlayer!=null){
                    mediaPlayer.stop();}
                    imgHint.setVisibility(View.INVISIBLE);
                }
            }
        });



        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });



        btnStartPlay.callOnClick();
    }

    private void UpdateTxtNumberOfQuestion() {
        txtQuestionNumber.setText(String.format(getString(R.string.question_n_of_10), Integer.toString(questionNumber)));
    }

    public  void CheckTheAnswer(Button givingAnswer){
        if(currentFlag.getName().equals(givingAnswer.getText().toString())){
            givingAnswer.setBackgroundColor(getResources().getColor( R.color.green));
            numberOfCorrectAnswers +=1;
        }
        else{
            givingAnswer.setBackgroundColor(getResources().getColor( R.color.red));
            numberOfWrongAnswers+=1;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GoToNextQuestion();
            }
        },
                (long)(Double.parseDouble(prefs.getString("delay","1.5")))*1000
            );

    }
    public void GoToNextQuestion(){

        if(questionNumber==10){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle(R.string.quiz_is_over)
                    .setMessage(String.format(getString(R.string.correct_answers_n_of_10),numberOfCorrectAnswers))
                    .setPositiveButton(R.string.start_new_quiz, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btnStartPlay.callOnClick();
                        }
                    })
                    .setCancelable(true)

            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    btnStartPlay.callOnClick();
                }
            })
            .show();
            return;
        }



        questionNumber += 1;
        UpdateTxtNumberOfQuestion();


        ArrayList<String> existed = new ArrayList<>();
        int r = (int)(Math.random() * ((flags.size()-1) + 1));
        currentFlag = flags.get(r);
        flags.remove(r);
        imgQuestion.setImageResource(currentFlag.getImageId());

        if( prefs.getString("numberOfAnswers","").equals("4")){

            frameLayoutAnswers = findViewById(R.id.frameLayoutAnswers);
            Fragment4Answers fragment = new Fragment4Answers(answers,currentFlag.getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(frameLayoutAnswers.getId(),fragment)
                    .commit();
        }
    }
    private boolean isExist(String imageId,ArrayList<String> existed){
        for (String i:existed) {
            if (i.equals( imageId)) {
                return true;
            }
        }
        return false;
    }
}


//int r = min + (int)(Math.random() * ((max - min) + 1));
