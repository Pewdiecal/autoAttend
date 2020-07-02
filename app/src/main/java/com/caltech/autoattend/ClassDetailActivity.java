package com.caltech.autoattend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClassDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ClassDetailAdapter classDetailAdapter;
    ClassDetailViewModel classDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        toolbar = findViewById(R.id.classDetailToolbar);
        recyclerView = findViewById(R.id.classDetailsRecyclerView);
        classDetailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ClassDetailViewModel.class);
        Bundle bundle = getIntent().getExtras();

        toolbar.setTitle(bundle.getString("Subject Name"));
        classDetailViewModel.getAllSession(bundle.getString("Subject Name")).observe(this, subjects -> {
            if (classDetailAdapter == null) {
                classDetailAdapter = new ClassDetailAdapter(subjects, getApplication());
                recyclerView.setAdapter(classDetailAdapter);
            } else {
                classDetailAdapter.updateData(subjects);
            }
            if (subjects.size() != 0) {
                toolbar.setBackgroundColor(Color.parseColor(subjects.get(0).colorHex));

            }

        });
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_class_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit:
                Intent intent = new Intent(this, AddSubject.class);
                Bundle bundle = new Bundle();
                bundle.putString("Subject Name", toolbar.getTitle().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;

            default:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}