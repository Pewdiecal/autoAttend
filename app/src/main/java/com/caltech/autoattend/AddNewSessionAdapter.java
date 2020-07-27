package com.caltech.autoattend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddNewSessionAdapter extends RecyclerView.Adapter<AddNewSessionAdapter.ViewHolder> {

    List<Subject> subjectList;
    String attendanceUrl;
    String time;
    String date;

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

    public AddNewSessionAdapter(List<Subject> subjectLists, String attendanceURL, String time, String date) {
        this.subjectList = subjectLists;
        this.attendanceUrl = attendanceURL;
        this.time = time;
        this.date = date;
    }

    @NonNull
    @Override
    public AddNewSessionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        signInStats.setText("");
        int STROKE_WIDTH = 8;
        holder.gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor(subjectList.get(position).colorHex));
        title.setText(subjectList.get(position).sub_name);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddSubject.class);
            Bundle bundle = new Bundle();
            bundle.putString("New Session", subjectList.get(position).sub_name);
            if (attendanceUrl != null) {
                bundle.putString("attendance url", attendanceUrl);
                bundle.putString("attendance time", time);
                bundle.putString("attendance date", date);
            }
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
            ((Activity) v.getContext()).finish();
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
