package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClassDetailViewModel extends AndroidViewModel {

    private DataRepo dataRepo;
    private LiveData<List<Subject>> subject;

    public ClassDetailViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
    }

    public LiveData<List<Subject>> getAllSession(String sub_name) {
        return dataRepo.getAllSession(sub_name);
    }

}
