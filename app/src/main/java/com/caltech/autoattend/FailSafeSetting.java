package com.caltech.autoattend;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

public class FailSafeSetting extends AppCompatActivity {
    Switch failSafe;
    ConstraintLayout intervalTime;
    TextView timeTxt;
    Toolbar toolbar;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_safe_setting);

        failSafe = findViewById(R.id.failSafeSwitch);
        intervalTime = findViewById(R.id.intervalTimeConst);
        timeTxt = findViewById(R.id.intervalTimeTxt);
        toolbar = findViewById(R.id.failSafeToolbar);

        toolbar.setTitle(R.string.SettingsFragments_FailSafeSettings);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        failSafe.setChecked(prefs.getBoolean("FailSafe status", true));
        timeTxt.setText(prefs.getInt("FailSafe timeInterval", 60) + " Seconds");

        intervalTime.setOnClickListener(v -> displayDialog());

        failSafe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("FailSafe status", isChecked);
            editor.commit();
        });
    }

    private void displayDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_custom_dialog, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Enter interval time");

        AlertDialog alertDialog = builder.create();

        TextInputLayout timeIntervalTxtInput = dialogView.findViewById(R.id.textInputLayout);
        EditText timeIntervalEdt = dialogView.findViewById(R.id.timeIntervalEdt);
        Button confirm = dialogView.findViewById(R.id.confirmDialogBtn);
        Button cancel = dialogView.findViewById(R.id.cancelDialogBtn);

        timeIntervalEdt.setText(String.valueOf(prefs.getInt("FailSafe timeInterval", 60)));

        confirm.setOnClickListener(v -> {
            if (timeIntervalEdt.getText().toString().isEmpty()) {
                timeIntervalTxtInput.setError("Time interval cannot be empty");
            } else {
                timeIntervalTxtInput.setError(null);
                timeTxt.setText(timeIntervalEdt.getText().toString() + " Seconds");
                editor.putInt("FailSafe timeInterval", Integer.parseInt(timeIntervalEdt.getText().toString()));
                editor.commit();
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}