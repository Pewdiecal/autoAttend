package com.caltech.autoattend;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

public class Onboard extends FragmentActivity {
    ViewPager2 viewpager;
    OnboardFragmentAdapter fragment1Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        viewpager = findViewById(R.id.viewPager2);
        fragment1Adapter = new OnboardFragmentAdapter(this);
        viewpager.setAdapter(fragment1Adapter);
    }
}