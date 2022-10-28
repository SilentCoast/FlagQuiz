package com.example.flagguizbysilentcoast.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flagguizbysilentcoast.MainActivity;
import com.example.flagguizbysilentcoast.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment4Answers extends Fragment {
    List<String> answers;
    private String correctAnswer;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    public Fragment4Answers(List<String> answers,String correctAnswer){
        super(R.layout.fragment_4_answers);
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = view.findViewById(R.id.btnAnsw1);
        btn2 = view.findViewById(R.id.btnAnsw2);
        btn3 = view.findViewById(R.id.btnAnsw3);
        btn4 = view.findViewById(R.id.btnAnsw4);
        Button[] btns = {btn1,btn2,btn3,btn4};


        int theCorrectAnswerPlace = (int)(Math.random() * ((btns.length-1) + 1));
        ArrayList<String> existed = new ArrayList<>();
        existed.add(correctAnswer);

        for (int i = 0; i < btns.length; i++) {
            int r =(int)(Math.random() * ((answers.size()-1) + 1));
            String answer = answers.get(r);
            if(i==theCorrectAnswerPlace){
                btns[i].setText(correctAnswer);
            }
            else{
                while(true){
                    if(!isExist(answer,existed)){
                        btns[i].setText(answer);
                        existed.add(answer);
                        break;
                    }
                    answer = answers.get ((int)(Math.random() * ((answers.size()-1) + 1)));

                }

            }
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    ((MainActivity)getActivity()).CheckTheAnswer(button);
                }
            });
        }

    }

    private boolean isExist(String answer,ArrayList<String> existed){
        for (String i:existed) {
            if (i.equals(answer)) {
                return true;
            }
        }
        return false;
    }
}
