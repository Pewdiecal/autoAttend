package com.caltech.autoattend;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassDetailAdapter extends RecyclerView.Adapter<ClassDetailAdapter.ViewHolder> {

    List<Subject> subjects;
    Application application;
    DataRepo dataRepo;

    public ClassDetailAdapter(List<Subject> subjects, Application application) {
        this.subjects = subjects;
        this.application = application;
        this.dataRepo = new DataRepo(application);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView classSessionTxt;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classSessionTxt = itemView.findViewById(R.id.classTimeTxt);
            imageView = itemView.findViewById(R.id.deleteTimeImg);
            constraintLayout = itemView.findViewById(R.id.timeConstrainLayout);
        }
    }

    @NonNull
    @Override
    public ClassDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View classDetailView = inflater.inflate(R.layout.view_class_time, parent, false);
        return new ViewHolder(classDetailView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassDetailAdapter.ViewHolder holder, int position) {
        TextView classSessionTxt = holder.classSessionTxt;
        ImageView imageView = holder.imageView;
        ConstraintLayout constraintLayout = holder.constraintLayout;
        String session = "(" + subjects.get(position).class_session + ") " + subjects.get(position).session_day + ", "
                + subjects.get(position).session_time_start + " - " + subjects.get(position).session_time_end;

        classSessionTxt.setText(session);
        imageView.setOnClickListener(v -> {

            dataRepo.deleteSession(subjects.get(position));
            subjects.remove(position);
            notifyDataSetChanged();
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSubject.class);
                Bundle bundle = new Bundle();
                bundle.putString("Session ID", subjects.get(position).session_id);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return subjects.size();
    }

    public void updateData(List<Subject> subject) {
        this.subjects = subject;
        notifyDataSetChanged();
    }
}
