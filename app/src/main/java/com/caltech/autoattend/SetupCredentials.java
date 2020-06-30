package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

public class SetupCredentials extends AppCompatActivity {

    TextInputLayout idInputLayout;
    TextInputLayout pwdInputLayout;
    EditText IDEdt;
    EditText pwdEdt;
    Button nextBtn;
    Button backBtn;
    User user;
    Intent mainIntent;
    SetupCredentialsViewModel credentialsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_credentials);

        idInputLayout = findViewById(R.id.idInputLayout_setup);
        pwdInputLayout = findViewById(R.id.pwdInputLayout_setup);
        IDEdt = findViewById(R.id.IDedt_setup);
        pwdEdt = findViewById(R.id.pwdedt_setup);
        nextBtn = findViewById(R.id.setup_nextbtn);
        backBtn = findViewById(R.id.setup_backbtn);
        mainIntent = new Intent(SetupCredentials.this, ScheduleSetup.class);
        credentialsViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SetupCredentialsViewModel.class);

        credentialsViewModel.getUserCredentials().observe(this, user -> SetupCredentials.this.user = user);

        if (user != null) {
            IDEdt.setText(user.student_id);
            pwdEdt.setText(user.password);
            nextBtn.setText(R.string.confirm_btn);
        }

        nextBtn.setEnabled(false);

        IDEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > idInputLayout.getCounterMaxLength()) {
                    idInputLayout.setError("Max character length is " + idInputLayout.getCounterMaxLength());
                } else if (IDEdt.getText().toString().isEmpty()) {
                    idInputLayout.setError("ID cannot be empty");
                } else if (s.length() <= idInputLayout.getCounterMaxLength() && idInputLayout.getError() != null) {
                    idInputLayout.setError(null);
                }

                if (!IDEdt.getText().toString().isEmpty() && !pwdEdt.getText().toString().isEmpty()) {
                    if (idInputLayout.getError() == null && pwdInputLayout.getError() == null) {
                        nextBtn.setEnabled(true);
                    } else {
                        nextBtn.setEnabled(false);
                    }
                } else {
                    nextBtn.setEnabled(false);
                }

            }
        });

        pwdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (pwdEdt.getText().toString().isEmpty()) {
                    pwdInputLayout.setError("Password cannot be empty");
                } else if (pwdInputLayout.getError() != null) {
                    pwdInputLayout.setError(null);
                }

                if (!IDEdt.getText().toString().isEmpty() && !pwdEdt.getText().toString().isEmpty()) {
                    if (idInputLayout.getError() == null && pwdInputLayout.getError() == null) {
                        nextBtn.setEnabled(true);
                    } else {
                        nextBtn.setEnabled(false);
                    }
                } else {
                    nextBtn.setEnabled(false);
                }
            }
        });

        pwdEdt.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (idInputLayout.getError() == null && pwdInputLayout.getError() == null && nextBtn.isEnabled()) {
                    if (user != null) {
                        user.student_id = IDEdt.getText().toString();
                        user.password = pwdEdt.getText().toString();
                        credentialsViewModel.updateUserCredential(user);
                        finish();
                    } else {
                        credentialsViewModel.insertUserCredential(IDEdt.getText().toString(), pwdEdt.getText().toString());
                        startActivity(mainIntent);
                    }

                }
                handled = true;
            }
            return handled;
        });

        backBtn.setOnClickListener(v -> finish());

        nextBtn.setOnClickListener(v -> {
            if (idInputLayout.getError() == null && pwdInputLayout.getError() == null) {
                if (user != null) {
                    user.student_id = IDEdt.getText().toString();
                    user.password = pwdEdt.getText().toString();
                    credentialsViewModel.updateUserCredential(user);
                    finish();
                } else {
                    credentialsViewModel.insertUserCredential(IDEdt.getText().toString(), pwdEdt.getText().toString());
                    startActivity(mainIntent);
                }

            }
        });

    }
}