package com.caltech.autoattend;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepo {

    private DAOs daOs;
    private Application application;
    private LiveData<User> user_credential;
    private LiveData<List<Subject>> allSubject;
    private LiveData<SemesterDate> semesterDate;

    DataRepo(Application application) {
        DataStorage dataStorage = DataStorage.getInstance(application);
        this.application = application;
        daOs = dataStorage.daos();
        user_credential = daOs.getUserCredential();
        allSubject = daOs.getAllSubject();
        semesterDate = daOs.getSemesterDate();
    }

    public void insertNewUser(User user) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertUserCredential(user));
    }

    public void insertNewSubject(Subject subject) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertNewSubject(subject));
    }

    public void insertNewSemesterDate(SemesterDate semesterDate) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertNewSemesterDate(semesterDate));
    }

    public void updateUserCredential(User user) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateUserCredential(user));
    }

    public void updateSubject(Subject subject) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateSubject(subject));
    }

    public void updateSemesterDate(SemesterDate semesterDate) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateSemesterDate(semesterDate));
    }

    public void deleteSubject(String sub_name) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.deleteSubject(sub_name));
    }

    public void deleteSession(Subject subject) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.deleteSession(subject));
    }

    public LiveData<User> getUser() {
        return user_credential;
    }

    public LiveData<List<Subject>> getAllSession(String sub_name) {
        return daOs.getAllSession(sub_name);
    }

    public LiveData<Subject> getSingleSession(String session_id) {
        return daOs.getSingleSession(session_id);
    }

    public LiveData<List<Subject>> getAllSubject() {
        return allSubject;
    }

    public LiveData<SemesterDate> getSemesterDate() {
        return semesterDate;
    }

    public LiveData<List<Subject>> checkSubject(String sub_name) {
        return daOs.checkSubject(sub_name);
    }

    public LiveData<Subject> checkSingleSession(String class_session, String session_time_start, String session_time_end, String sub_name) {
        return daOs.checkSingleSession(class_session, session_time_start, session_time_end, sub_name);
    }

    public void nukeAllTable() {
        DataStorage.databaseWriteExecutor.execute(() -> DataStorage.getInstance(application).clearAllTables());
    }

}
