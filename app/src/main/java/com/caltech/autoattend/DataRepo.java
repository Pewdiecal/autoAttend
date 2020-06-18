package com.caltech.autoattend;

import android.app.Application;

public class DataRepo {

    private DAOs daOs;
    private User user_credential;
    private Subject subject;
    private SessionSchedule sessionSchedule;
    private ClassSession classSession;
    private Application application;

    DataRepo(Application application) {
        DataStorage.databaseWriteExecutor.execute(() -> {
            DataStorage dataStorage = DataStorage.getInstance(application);
            this.application = application;
            daOs = dataStorage.daos();
            user_credential = daOs.getUserCredential();
        });
        //TODO: Add all the data that needed to fetch
    }

    public void insertNewUser(User user) {
        DataStorage.databaseWriteExecutor.execute(() -> {
            daOs.insertUserCredential(user);
        });
    }

    public User getUser() {
        return user_credential;
    }

    public void nukeAllTable() {
        DataStorage.databaseWriteExecutor.execute(() -> {
            DataStorage.getInstance(application).clearAllTables();
        });
    }

}
