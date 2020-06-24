package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int backCounter;
    BottomAppBar bottomAppBar;
    ViewPager2 viewPager2;
    MainFragmentAdapter mainFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottomAppBar4);
        viewPager2 = findViewById(R.id.host_viewPager2);
        mainFragmentAdapter = new MainFragmentAdapter(this);
        viewPager2.setAdapter(mainFragmentAdapter);

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