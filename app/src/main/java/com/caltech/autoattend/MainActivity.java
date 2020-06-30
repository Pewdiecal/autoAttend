package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int backCounter;
    BottomAppBar bottomAppBar;
    ViewPager2 viewPager2;
    MainFragmentAdapter mainFragmentAdapter;
    FloatingActionButton mainAddFab;
    FloatingActionButton qrCodeFab;
    FloatingActionButton linkFab;
    ConstraintLayout qrCodeLayout;
    ConstraintLayout linkLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottomAppBar4);
        viewPager2 = findViewById(R.id.host_viewPager2);
        mainAddFab = findViewById(R.id.add_fab);
        qrCodeFab = findViewById(R.id.qr_code_fab);
        linkFab = findViewById(R.id.link_fab);
        qrCodeLayout = findViewById(R.id.qrcode_layout);
        linkLayout = findViewById(R.id.link_layout);
        mainFragmentAdapter = new MainFragmentAdapter(this);
        viewPager2.setAdapter(mainFragmentAdapter);
        Animation fabShow = AnimationUtils.loadAnimation(this, R.anim.fab_show);
        Animation fabHide = AnimationUtils.loadAnimation(this, R.anim.fab_hide);
        Animation fabFadeIn = AnimationUtils.loadAnimation(this, R.anim.fab_fade_in);
        Animation fabFadeOut = AnimationUtils.loadAnimation(this, R.anim.fab_fade_out);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                backCounter = 0;
            }
        }, 2000, 2000);

        bottomAppBar.setNavigationOnClickListener(v -> BottomSheet.newInstance().show(getSupportFragmentManager(), "bottom_sheet_menu"));

        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {

            }
            return false;
        });

        mainAddFab.setOnClickListener(v -> {
            bottomAppBar.performShow();
            if (qrCodeLayout.getVisibility() == View.VISIBLE && linkLayout.getVisibility() == View.VISIBLE) {
                qrCodeLayout.setVisibility(View.GONE);
                linkLayout.setVisibility(View.GONE);
                mainAddFab.startAnimation(fabHide);
                qrCodeLayout.startAnimation(fabFadeOut);
                linkLayout.startAnimation(fabFadeOut);
            } else {
                qrCodeLayout.setVisibility(View.VISIBLE);
                linkLayout.setVisibility(View.VISIBLE);
                mainAddFab.startAnimation(fabShow);
                qrCodeLayout.startAnimation(fabFadeIn);
                linkLayout.startAnimation(fabFadeIn);
            }
        });

        linkFab.setOnClickListener(v -> {
            qrCodeLayout.setVisibility(View.GONE);
            linkLayout.setVisibility(View.GONE);
            mainAddFab.startAnimation(fabHide);
            qrCodeLayout.startAnimation(fabFadeOut);
            linkLayout.startAnimation(fabFadeOut);
            Intent mainIntent = new Intent(this, AddSubject.class);
            startActivity(mainIntent);
        });

        qrCodeFab.setOnClickListener(v -> {
            qrCodeLayout.setVisibility(View.GONE);
            linkLayout.setVisibility(View.GONE);
            mainAddFab.startAnimation(fabHide);
            qrCodeLayout.startAnimation(fabFadeOut);
            linkLayout.startAnimation(fabFadeOut);
            Intent mainIntent = new Intent(this, QRScannerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ActivitySource", "qrCodeFab");
            mainIntent.putExtras(bundle);
            startActivity(mainIntent);
        });


    }

    @Override
    public void onBackPressed() {

        if (backCounter == 1) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backCounter++;
    }

}