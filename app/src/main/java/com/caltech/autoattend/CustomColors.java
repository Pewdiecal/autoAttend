package com.caltech.autoattend;

import androidx.annotation.NonNull;

public class CustomColors {
    public String color;
    public int colorImgSource;

    CustomColors(String color, int colorImgSource) {
        this.color = color;
        this.colorImgSource = colorImgSource;
    }

    public String getColor() {
        return color;
    }

    public int getColorImgSource() {
        return colorImgSource;
    }

    @NonNull
    @Override
    public String toString() {
        return this.color;
    }
}
