package com.caltech.autoattend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private User user;
    private SemesterDate semesterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);

        mainActivityViewModel.getUserCredentials().observe(this, user -> MainActivity.this.user = user);

        mainActivityViewModel.getSemesterDate().observe(this, semesterDate -> MainActivity.this.semesterDate = semesterDate);

        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */

            if (user != null && semesterDate != null) {

                Intent mainIntent = new Intent(MainActivity.this, SubjectList.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(mainIntent, options.toBundle());
            } else {
                mainActivityViewModel.nukeAllTable();

                Intent mainIntent = new Intent(MainActivity.this, Onboard.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(mainIntent, options.toBundle());
            }
            finish();

        }, SPLASH_DISPLAY_LENGTH);
    }


}