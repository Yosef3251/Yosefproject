package com.yosef.yosefproject1011.QuestionPack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yosef.yosefproject1011.R;

import java.util.List;

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.ViewHolder> {

    private List<Question> mData;
    private LayoutInflater mInflater;
    private Context context;
    private AdapterQuestion.ItemClickListener mClickListener;

    // data is passed into the constructor
    AdapterQuestion(Context context, List<Question> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public AdapterQuestion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_question, parent, false);
        return new AdapterQuestion.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AdapterQuestion.ViewHolder holder, int position) {
        Question Que = mData.get(position);
        holder.tvQuestion.setText(Que.getQuestion());
        holder.tvQuestion.setText(Que.getQuestion());
        holder.tvQuestion.setText(Que.getQuestion());

        //holder.ivPhoto.setImageDrawable(rest.getPhoto());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestion  ;
        ImageView ivQuestion;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestionRow);
            ivQuestion = itemView.findViewById(R.id.ivNumQuestionRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Question getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(AdapterQuestion.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
