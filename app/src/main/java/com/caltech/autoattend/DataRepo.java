package com.caltech.autoattend;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class DataRepo {

    private DAOs daOs;
    private Application application;
    private LiveData<User> user_credential;
    private LiveData<Subject> subject;
    private LiveData<SessionSchedule> sessionSchedule;
    private LiveData<ClassSession> classSession;
    private LiveData<SemesterDate> semesterDate;

    DataRepo(Application application) {
        DataStorage dataStorage = DataStorage.getInstance(application);
        this.application = application;
        daOs = dataStorage.daos();
        user_credential = daOs.getUserCredential();
        subject = daOs.getSubject();
        sessionSchedule = daOs.getSessionSchedule();
        classSession = daOs.getClassSession();
        semesterDate = daOs.getSemesterDate();

    }

    public void insertNewUser(User user) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertUserCredential(user));
    }

    public void insertNewSubject(Subject subject) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertNewSubject(subject));
    }

    public void insertNewSessionSchedule(SessionSchedule sessionSchedule) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertNewSessionSchedule(sessionSchedule));
    }

    public void insertNewClassSession(ClassSession classSession) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.insertNewClassSession(classSession));
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

    public void updateSessionSchedule(SessionSchedule sessionSchedule) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateSessionSchedule(sessionSchedule));
    }

    public void updateClassSession(ClassSession classSession) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateClassSession(classSession));
    }

    public void updateSemesterDate(SemesterDate semesterDate) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.updateSemesterDate(semesterDate));
    }

    public void deleteSubject(Subject subject) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.deleteSubject(subject));
    }

    public void deleteSessionSchedule(SessionSchedule sessionSchedule) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.deleteSessionSchedule(sessionSchedule));
    }

    public void deleteClassSession(ClassSession classSession) {
        DataStorage.databaseWriteExecutor.execute(() -> daOs.deleteClassSession(classSession));
    }

    public LiveData<User> getUser() {
        return user_credential;
    }

    public LiveData<Subject> getSubject() {
        return subject;
    }

    public LiveData<SessionSchedule> getSessionSchedule() {
        return sessionSchedule;
    }

    public LiveData<ClassSession> getClassSession() {
        return classSession;
    }

    public LiveData<SemesterDate> getSemesterDate() {
        return semesterDate;
    }

    public void nukeAllTable() {
        DataStorage.databaseWriteExecutor.execute(() -> DataStorage.getInstance(application).clearAllTables());
    }

}
