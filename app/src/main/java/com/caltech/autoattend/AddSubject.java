package com.caltech.autoattend;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddSubject extends AppCompatActivity {
    Toolbar toolbar;
    TextInputLayout colorInputLayout;
    TextInputLayout subjectInputLayout;
    TextInputLayout timeEndInputLayout;
    TextInputLayout timeStartInputLayout;
    TextInputLayout linkInputLayout;
    TextInputLayout classInputLayout;
    TextInputLayout dayInputLayout;
    AutoCompleteTextView colorTxt;
    AutoCompleteTextView timeEndTxt;
    AutoCompleteTextView timeStartTxt;
    AutoCompleteTextView dayTxt;
    EditText subjectEdt;
    EditText linkEdt;
    AutoCompleteTextView classTxt;
    Button scanQR;
    AddSubjectViewModel addSubjectViewModel;
    StringBuilder session_id;
    Random random;
    LiveData<List<Subject>> liveData;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        toolbar = findViewById(R.id.toolbar_addSub);
        colorInputLayout = findViewById(R.id.color_txt);
        subjectInputLayout = findViewById(R.id.subjectInputLayout_addSub);
        timeEndInputLayout = findViewById(R.id.timeEnd_txt);
        timeStartInputLayout = findViewById(R.id.timeStart_txt);
        linkInputLayout = findViewById(R.id.linkInputLayout_addSub);
        classInputLayout = findViewById(R.id.classInputLayout_addSub);
        colorTxt = findViewById(R.id.color_spinner);
        subjectEdt = findViewById(R.id.subjectedt_addSub);
        timeEndTxt = findViewById(R.id.timeEnd_spinner);
        timeStartTxt = findViewById(R.id.timeStart_spinner);
        linkEdt = findViewById(R.id.linkedt_addSub);
        classTxt = findViewById(R.id.classSessionTxt);
        scanQR = findViewById(R.id.addSub_scanQRbtn);
        dayTxt = findViewById(R.id.day_spinner);
        dayInputLayout = findViewById(R.id.day_txt);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.remove("attendance url");
        editor.commit();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            linkEdt.setText(bundle.getString("attendance url"));
        }

        random = new Random();
        addSubjectViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AddSubjectViewModel.class);

        toolbar.setTitle(R.string.addSub_title);
        setSupportActionBar(toolbar);

        TimePickerDialog timeStartPickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String timeMin = String.format(Locale.US, "%02d", minute);
                    String timeHrs = String.format(Locale.US, "%02d", hourOfDay);
                    timeStartTxt.setText(timeHrs + ":" + timeMin);

                }, 8, 0, true);

        TimePickerDialog timeEndPickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {

                    String timeMin = String.format(Locale.US, "%02d", minute);
                    String timeHrs = String.format(Locale.US, "%02d", hourOfDay);
                    timeEndTxt.setText(timeHrs + ":" + timeMin);

                }, 8, 0, true);


        ArrayAdapter<String> daysArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                addSubjectViewModel.getDays());

        ArrayAdapter<String> classSessionAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                addSubjectViewModel.getClassSessions());

        ColorArrayAdapter customColorsArrayAdapter = new ColorArrayAdapter(this, 0, addSubjectViewModel.getCustomColors());

        dayTxt.setAdapter(daysArrayAdapter);

        classTxt.setAdapter(classSessionAdapter);

        colorTxt.setAdapter(customColorsArrayAdapter);

        timeStartInputLayout.setEndIconOnClickListener(v -> timeStartPickerDialog.show());

        timeEndInputLayout.setEndIconOnClickListener(v -> timeEndPickerDialog.show());

        subjectEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (subjectInputLayout.getError() != null) {
                    subjectInputLayout.setError(null);
                }
                if (s.toString().startsWith(" ")) {
                    subjectInputLayout.setError("Cannot begin with whitespace character");
                }
            }
        });

        classTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (classInputLayout.getError() != null) {
                    classInputLayout.setError(null);
                }
                if (s.toString().startsWith(" ")) {
                    classInputLayout.setError("Cannot begin with whitespace character");
                }
            }
        });

        dayTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dayInputLayout.getError() != null) {
                    dayInputLayout.setError(null);
                }
            }
        });

        timeStartTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (timeStartInputLayout.getError() != null) {
                    timeStartInputLayout.setError(null);
                }
            }
        });

        timeEndTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (timeEndInputLayout.getError() != null) {
                    timeEndInputLayout.setError(null);
                }
            }
        });

        linkEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (linkInputLayout.getError() != null) {
                    linkInputLayout.setError(null);
                }
                if (s.toString().startsWith(" ")) {
                    linkInputLayout.setError("Cannot begin with whitespace character");
                }
            }
        });

        colorTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (colorInputLayout.getError() != null) {
                    colorInputLayout.setError(null);
                }
            }
        });

        scanQR.setOnClickListener(v -> {
            Intent mainIntent = new Intent(AddSubject.this, QRScannerActivity.class);
            startActivity(mainIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addsubject, menu);
        return true;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addSub_save:

                session_id = new StringBuilder(subjectEdt.getText().toString()).append(classTxt.getText().toString()).append(random.nextInt(2000));

                if (isErrorFree()) {
                    if (toolbar.getTitle().equals(getString(R.string.addSub_title))) {
                        liveData = addSubjectViewModel.getSubject(subjectEdt.getText().toString());

                        liveData.observe(this, subjects -> {
                            if (subjects.size() != 0) {

                                if (addSubjectViewModel.checkSingleSession(subjectEdt.getText().toString(), timeStartTxt.getText().toString(),
                                        timeEndTxt.getText().toString(), subjectEdt.getText().toString())) {

                                    alertDialog(subjects.get(0));

                                } else {
                                    addSubjectViewModel.insertNewSubject(subjectEdt.getText().toString(), subjects.get(0).colorHex,
                                            session_id.toString(), classTxt.getText().toString(), timeStartTxt.getText().toString(),
                                            timeEndTxt.getText().toString(), dayTxt.getText().toString(), linkEdt.getText().toString());
                                    finish();
                                }

                            } else {
                                addSubjectViewModel.insertNewSubject(subjectEdt.getText().toString(),
                                        addSubjectViewModel.getColorHex().get(colorTxt.getText().toString()),
                                        session_id.toString(), classTxt.getText().toString(), timeStartTxt.getText().toString(),
                                        timeEndTxt.getText().toString(), dayTxt.getText().toString(), linkEdt.getText().toString());
                                finish();

                            }

                            liveData.removeObservers(AddSubject.this);
                        });

                    } else {

                        /*addSubjectViewModel.updateSubject(subject.get(0), session_id.toString(), classTxt.getText().toString(),
                                timeStartTxt.getText().toString(), timeEndTxt.getText().toString(), dayTxt.getText().toString(),
                                addSubjectViewModel.getColorHex().get(colorTxt.getText().toString()), linkEdt.getText().toString());*/
                    }

                }
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean isErrorFree() {
        if (subjectEdt.getText().toString().isEmpty()) {
            subjectInputLayout.setError("Subject cannot be empty");
        }
        if (classTxt.getText().toString().isEmpty()) {
            classInputLayout.setError("Class session cannot be empty");
        }
        if (colorTxt.getText().toString().isEmpty()) {
            colorInputLayout.setError("Please choose a color tag");
        }
        if (dayTxt.getText().toString().isEmpty()) {
            dayInputLayout.setError("Please select a day");
        }
        if (timeStartTxt.getText().toString().isEmpty()) {
            timeStartInputLayout.setError("Time cannot be empty");
        }
        if (timeEndTxt.getText().toString().isEmpty()) {
            timeEndInputLayout.setError("Time cannot be empty");
        }
        if (linkEdt.getText().toString().isEmpty()) {
            linkInputLayout.setError("Link cannot be empty");
        }

        return subjectInputLayout.getError() == null && classInputLayout.getError() == null && colorInputLayout.getError() == null
                && dayInputLayout.getError() == null && timeStartInputLayout.getError() == null && timeEndInputLayout.getError() == null
                && linkInputLayout.getError() == null;

    }

    public void alertDialog(Subject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.addSub_alertDialog_msg)
                .setPositiveButton(R.string.addSub_alertDialog_merge, (dialog, id) -> {
                    // FIRE ZE MISSILES!
                    finish();
                })
                .setNegativeButton(R.string.addSub_alertDialog_duplicate, (dialog, id) -> {
                    // User cancelled the dialog
                    addSubjectViewModel.insertNewSubject(subject.sub_name, subject.colorHex,
                            session_id.toString(), classTxt.getText().toString(), timeStartTxt.getText().toString(),
                            timeEndTxt.getText().toString(), dayTxt.getText().toString(), linkEdt.getText().toString());
                    finish();
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStop() {
        editor.remove("attendance url");
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (!prefs.getString("attendance url", "").equals("")) {
            linkEdt.setText(prefs.getString("attendance url", ""));
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}