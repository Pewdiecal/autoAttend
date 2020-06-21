package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;

public class Onboard extends AppCompatActivity {
    ViewPager2 viewpager;
    OnboardFragmentAdapter fragment1Adapter;
    TabLayout tabLayout;
    Button getStartedbtn;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard);


        handler = new Handler();
        viewpager = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout2);
        getStartedbtn = findViewById(R.id.button);
        fragment1Adapter = new OnboardFragmentAdapter(this);
        viewpager.setAdapter(fragment1Adapter);
        new TabLayoutMediator(tabLayout, viewpager, (tab, position) -> tab.view.setClickable(false)).attach();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (viewpager.getScrollState() != SCROLL_STATE_DRAGGING) {
                    if (viewpager.getCurrentItem() == 2) {
                        handler.post(() -> {
                            viewpager.setCurrentItem(0);
                        });
                    } else {
                        handler.post(() -> {
                            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                        });
                    }
                }
            }
        }, 3000, 3000);

        getStartedbtn.setOnClickListener(v -> {
            Intent mainIntent = new Intent(Onboard.this, SetupCredentials.class);
            startActivity(mainIntent);
        });

    }
}