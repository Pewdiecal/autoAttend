package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SetupCredentialsViewModel extends AndroidViewModel {

    private LiveData<User> userCredentials;
    private DataRepo dataRepo;

    public SetupCredentialsViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        userCredentials = dataRepo.getUser();
    }

    public void insertUserCredential(String stu_id, String pwd) {
        dataRepo.insertNewUser(new User(1, stu_id, pwd));
    }

    public void updateUserCredential(User user) {
        dataRepo.updateUserCredential(user);

    }

    public LiveData<User> getUserCredentials() {
        return userCredentials;
    }

}
