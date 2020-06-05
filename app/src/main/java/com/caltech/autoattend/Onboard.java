package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Onboard extends AppCompatActivity {
    ViewPager2 viewpager;
    OnboardFragmentAdapter fragment1Adapter;
    TabLayout tabLayout;
    Button getStartedbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard);

        viewpager = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout2);
        getStartedbtn = findViewById(R.id.button);
        fragment1Adapter = new OnboardFragmentAdapter(this);
        viewpager.setAdapter(fragment1Adapter);
        new TabLayoutMediator(tabLayout, viewpager, (tab, position) -> tab.view.setClickable(false)).attach();

        getStartedbtn.setOnClickListener(v -> {
            Intent mainIntent = new Intent(Onboard.this, CredentialSettings.class);
            startActivity(mainIntent);
        });
    }
}