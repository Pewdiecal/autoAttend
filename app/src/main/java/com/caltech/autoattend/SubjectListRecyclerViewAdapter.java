package com.caltech.autoattend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectListRecyclerViewAdapter extends RecyclerView.Adapter<SubjectListRecyclerViewAdapter.ViewHolder> {

    List<Subject> subjectList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectTitle;
        public TextView lastSignIn;
        public CardView cardView;
        public ConstraintLayout constraintLayout;
        public GradientDrawable gradientDrawable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTitle = itemView.findViewById(R.id.subjectTitleTxt);
            lastSignIn = itemView.findViewById(R.id.lastSignTxt);
            cardView = itemView.findViewById(R.id.subjectCardView);
            constraintLayout = itemView.findViewById(R.id.cardConstrainLayout);
            gradientDrawable = (GradientDrawable) constraintLayout.getBackground();
        }
    }

    public SubjectListRecyclerViewAdapter(List<Subject> subjectLists) {
        this.subjectList = subjectLists;
    }

    @NonNull
    @Override
    public SubjectListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View subjectView = inflater.inflate(R.layout.view_subject_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(subjectView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView title = holder.subjectTitle;
        TextView signInStats = holder.lastSignIn;
        int STROKE_WIDTH = 8;
        holder.gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor(subjectList.get(position).colorHex));
        title.setText(subjectList.get(position).sub_name);
        signInStats.setText("LAST SIGN IN");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public void updateData(List<Subject> subjectList) {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }
}
