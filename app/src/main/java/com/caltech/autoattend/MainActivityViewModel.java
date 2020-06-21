package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<User> userCredentials;
    private LiveData<SemesterDate> semesterDate;
    private DataRepo dataRepo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        userCredentials = dataRepo.getUser();
        semesterDate = dataRepo.getSemesterDate();
    }

    public LiveData<User> getUserCredentials() {
        return userCredentials;
    }

    public LiveData<SemesterDate> getSemesterDate() {
        return semesterDate;
    }

    public void nukeAllTable() {
        dataRepo.nukeAllTable();
    }
}
