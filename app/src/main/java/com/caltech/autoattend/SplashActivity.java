package com.caltech.autoattend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SplashActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private User user;
    private SemesterDate semesterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        mainActivityViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);

        mainActivityViewModel.getUserCredentials().observe(this, user -> SplashActivity.this.user = user);

        mainActivityViewModel.getSemesterDate().observe(this, semesterDate -> SplashActivity.this.semesterDate = semesterDate);

        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */

            if (user != null && semesterDate != null) {

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                startActivity(mainIntent, options.toBundle());
            } else {
                mainActivityViewModel.nukeAllTable();

                Intent mainIntent = new Intent(SplashActivity.this, Onboard.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                startActivity(mainIntent, options.toBundle());
            }
            finish();

        }, SPLASH_DISPLAY_LENGTH);
    }


}