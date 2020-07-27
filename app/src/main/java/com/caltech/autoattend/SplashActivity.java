package com.caltech.autoattend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private User user;
    private String semesterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        mainActivityViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);

        mainActivityViewModel.getUserCredentials().observe(this, user -> SplashActivity.this.user = user);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        semesterDate = prefs.getString("Semester Date End", null);

        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */

            if (user != null && semesterDate != null) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                String sysDate = df.format(new Date());

                if (!checkDate(sysDate, semesterDate)) {
                    alertDialog();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                    startActivity(mainIntent, options.toBundle());
                    finish();
                }

            } else {
                mainActivityViewModel.nukeAllTable();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Semester Date Start", null);
                editor.putString("Semester Date End", null);
                editor.commit();

                Intent mainIntent = new Intent(SplashActivity.this, Onboard.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                startActivity(mainIntent, options.toBundle());
                finish();
            }


        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean checkDate(String strDateStart, String strDateEnd) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        Date dateStart = null;
        Date dateEnd = null;
        Date dateSystem = null;
        try {
            dateStart = df.parse(strDateStart);
            dateEnd = df.parse(strDateEnd);
            dateSystem = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return !dateEnd.before(dateStart) && !dateEnd.before(dateSystem);
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.splash_date_alert)
                .setPositiveButton(R.string.splash_extend_btn, (dialog, id) -> {
                    Intent intent = new Intent(SplashActivity.this, ScheduleSetup.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.splash_createNew_btn, (dialog, id) -> {

                    alertClearAllSubsDialog();
                });

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    private void alertClearAllSubsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.splash_subClear_alert)
                .setPositiveButton(R.string.splash_yes_btn, (dialog, id) -> {
                    Intent intent = new Intent(SplashActivity.this, ScheduleSetup.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.splash_createNew_btn, (dialog, id) -> {
                    alertDialog();
                });

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

}