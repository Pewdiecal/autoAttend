package com.caltech.autoattend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ColorArrayAdapter extends ArrayAdapter<CustomColors> {
    private ArrayList<CustomColors> customColors;

    public ColorArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CustomColors> objects) {
        super(context, resource, objects);
        customColors = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.color_exposed_dropdown, parent, false);
        }

        TextView colors = listItemView.findViewById(R.id.colorSpinnerTxt);
        ImageView colorImg = listItemView.findViewById(R.id.colorImg);
        colors.setText(customColors.get(position).color);
        colorImg.setImageResource(customColors.get(position).colorImgSource);

        return listItemView;
    }
}
