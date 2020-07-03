package com.caltech.autoattend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddNewSessionActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AddNewSessionViewModel addNewSessionViewModel;
    AddNewSessionAdapter addNewSessionAdapter;
    ArrayList<Subject> nonDuplicateSubjectList = new ArrayList<>();
    String intentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_session);

        toolbar = findViewById(R.id.AddNewSessiontoolbar);
        recyclerView = findViewById(R.id.AddNewSessionRV);
        addNewSessionViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AddNewSessionViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            intentData = bundle.getString("attendance url");
        }
        toolbar.setTitle(R.string.AddNewSessionActivity_title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        addNewSessionViewModel.getAllSubject().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                nonDuplicateSubjectList.clear();

                for (Subject subject : subjects) {
                    boolean isElementExists = false;
                    if (nonDuplicateSubjectList.isEmpty()) {
                        nonDuplicateSubjectList.add(subject);
                    } else {
                        for (int i = 0; i < nonDuplicateSubjectList.size(); i++) {
                            if (!nonDuplicateSubjectList.get(i).sub_name.equals(subject.sub_name)) {
                                if (i == nonDuplicateSubjectList.size() - 1 && !isElementExists) {
                                    nonDuplicateSubjectList.add(subject);
                                }
                            } else {
                                isElementExists = true;
                            }
                        }
                    }

                }

                if (addNewSessionAdapter == null) {
                    if (intentData != null) {
                        addNewSessionAdapter = new AddNewSessionAdapter(nonDuplicateSubjectList, intentData);
                    } else {
                        addNewSessionAdapter = new AddNewSessionAdapter(nonDuplicateSubjectList, null);
                    }
                    recyclerView.setAdapter(addNewSessionAdapter);
                } else {
                    addNewSessionAdapter.updateData(nonDuplicateSubjectList);
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_new_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addNewSession_create:
                Intent intent = new Intent(this, AddSubject.class);
                if (intentData != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("attendance url", intentData);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
                finish();
                break;
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}