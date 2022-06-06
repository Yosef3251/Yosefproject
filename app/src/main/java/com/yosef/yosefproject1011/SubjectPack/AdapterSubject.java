package com.yosef.yosefproject1011.SubjectPack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yosef.yosefproject1011.R;
import com.yosef.yosefproject1011.UserDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolder>{

    private List<Subject> mData;
    private LayoutInflater mInflater;
    private Context context;

    private final AdapterSubject.ItemClickListener mClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Subject subject = mData.get(position);
            Intent i = new Intent(context, UserDetailsActivity.class);
            //i.putExtra("subject", subject);

            context.startActivity(i);
        }
    };

    // data is passed into the constructor
    public AdapterSubject(Context context, ArrayList<Subject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public AdapterSubject.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_subject, parent, false);
        return new AdapterSubject.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AdapterSubject.ViewHolder holder, int position) {
        Subject subject = mData.get(position);
        holder.myTextView.setText(subject.getSubject());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvSubjectRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Subject getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
