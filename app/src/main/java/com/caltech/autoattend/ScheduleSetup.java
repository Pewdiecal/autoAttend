package com.caltech.autoattend;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleSetup extends AppCompatActivity {
    TextInputLayout startDateTxtInput;
    TextInputLayout endDateTxtInput;
    AutoCompleteTextView startDateTxt;
    AutoCompleteTextView endDateTxt;
    Button backBtn;
    Button finishBtn;
    ScheduleSetupViewModel viewModel;
    SemesterDate semesterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_setup);

        startDateTxtInput = findViewById(R.id.startDateInputLayout);
        endDateTxtInput = findViewById(R.id.endDateInputLayout);
        startDateTxt = findViewById(R.id.startDateTxt);
        endDateTxt = findViewById(R.id.endDateTxt);
        backBtn = findViewById(R.id.schedule_backbtn);
        finishBtn = findViewById(R.id.schedule_finishbtn);
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ScheduleSetupViewModel.class);

        viewModel.getSemesterDate().observe(this, semesterDate -> ScheduleSetup.this.semesterDate = semesterDate);

        if (semesterDate != null) {
            finishBtn.setText(R.string.confirm_btn);
            startDateTxt.setText(semesterDate.startDate);
            endDateTxt.setText(semesterDate.endDate);
        }

        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy", Locale.UK);
        SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.UK);
        SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.UK);

        int mYear = Integer.parseInt(yyyy.format(new Date()));
        int mMonth = Integer.parseInt(mm.format(new Date())) - 1;
        int mDay = Integer.parseInt(dd.format(new Date()));

        startDateTxtInput.setEndIconOnClickListener(v -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> startDateTxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year), mYear, mMonth, mMonth);
            datePickerDialog.show();
        });

        endDateTxtInput.setEndIconOnClickListener(v -> {
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> endDateTxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        backBtn.setOnClickListener(v -> finish());

        finishBtn.setOnClickListener(v -> {
            if (!viewModel.insertDate(startDateTxt.getText().toString(), endDateTxt.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Invalid date input. Try again", Toast.LENGTH_LONG).show();
            } else {
                Intent mainIntent = new Intent(ScheduleSetup.this, MainActivity.class);

                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(ScheduleSetup.this);
                startActivity(mainIntent, options.toBundle());
                finish();
            }
        });

    }

}