package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class QRScannerViewModel extends AndroidViewModel {
    private DataRepo dataRepo;
    private LiveData<User> userLiveData;

    public QRScannerViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        userLiveData = dataRepo.getUser();

    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
