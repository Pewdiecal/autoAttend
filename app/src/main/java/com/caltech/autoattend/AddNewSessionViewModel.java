package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AddNewSessionViewModel extends AndroidViewModel {

    private LiveData<List<Subject>> allSubject;
    private DataRepo dataRepo;

    public AddNewSessionViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        allSubject = dataRepo.getAllSubject();
    }

    public LiveData<List<Subject>> getAllSubject() {
        return allSubject;
    }
}
