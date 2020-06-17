package com.caltech.autoattend;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class SetupCredentials extends AppCompatActivity {

    TextInputLayout idInputLayout;
    TextInputLayout pwdInputLayout;
    EditText IDEdt;
    EditText pwdEdt;
    Button nextBtn;
    Button backBtn;

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
                } else if (s.length() == idInputLayout.getCounterMaxLength()) {
                    idInputLayout.setError(null);
                }
            }
        });

        IDEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}