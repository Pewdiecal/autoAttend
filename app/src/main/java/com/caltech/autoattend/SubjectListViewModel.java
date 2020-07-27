package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SubjectListViewModel extends AndroidViewModel {
    private LiveData<List<Subject>> allSubject;
    private LiveData<List<Subject>> allSessions;
    private DataRepo dataRepo;

    public SubjectListViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        allSubject = dataRepo.getAllSubject();
    }

    public LiveData<List<Subject>> getAllSubject() {
        return allSubject;
    }

    public LiveData<List<Subject>> getAllSessions(String sub_name) {
        return dataRepo.getAllSession(sub_name);
    }

}