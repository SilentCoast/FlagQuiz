
package com.example.flagguizbysilentcoast.Classes;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.flagguizbysilentcoast.R;

        import java.util.List;


public class AnswersRecyclerAdapter extends RecyclerView.Adapter<AnswersRecyclerAdapter.ViewHolder> {
    List<String> answers;
    LayoutInflater layoutInflater;
    ItemClickListener mClickListener;
    public AnswersRecyclerAdapter(Context context, List<String> answers){
        layoutInflater = LayoutInflater.from(context);
        this.answers = answers;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.answer_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.imageView.setImageDrawable(answers.get(position).getImage());
//        holder.textViewName.setText(answers.get(position).getName());
        holder.txtCountryName.setText(answers.get(position));
    }

    public String getItem(int id){
        return answers.get(id);
    }
    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void setClicklistener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        ImageView imageView;
        TextView txtCountryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.imageViewInItem);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener!=null) mClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
}
