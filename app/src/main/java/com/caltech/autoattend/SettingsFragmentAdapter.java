package com.caltech.autoattend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragmentAdapter extends RecyclerView.Adapter<SettingsFragmentAdapter.ViewHolder> {
    SettingsItem[] settingsItemArray;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.settingsItemImg);
            textView = itemView.findViewById(R.id.settingsItemTxt);
            constraintLayout = itemView.findViewById(R.id.settingsConstrainLayout);
        }
    }

    SettingsFragmentAdapter(SettingsItem[] settingsItemArray) {
        this.settingsItemArray = settingsItemArray;
    }


    @NonNull
    @Override
    public SettingsFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View settingView = inflater.inflate(R.layout.view_setting_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(settingView);

    }

    @Override
    public void onBindViewHolder(@NonNull SettingsFragmentAdapter.ViewHolder holder, int position) {
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;
        ConstraintLayout constraintLayout = holder.constraintLayout;

        imageView.setImageResource(settingsItemArray[position].imageRes);
        textView.setText(settingsItemArray[position].settingsItem);

        constraintLayout.setOnClickListener(v -> {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(v.getContext(), SetupCredentials.class);
                    v.getContext().startActivity(intent);
                    break;

                case 1:
                    intent = new Intent(v.getContext(), FailSafeSetting.class);
                    v.getContext().startActivity(intent);
                    break;

                case 2:
                    intent = new Intent(v.getContext(), NotificationSetting.class);
                    v.getContext().startActivity(intent);
                    break;

                case 3:
                    intent = new Intent(v.getContext(), HelpActivity.class);
                    v.getContext().startActivity(intent);
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return settingsItemArray.length;
    }
}
