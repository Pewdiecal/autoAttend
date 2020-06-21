package com.caltech.autoattend;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOs {

    @Insert
    void insertUserCredential(User user);

    @Insert
    void insertNewSubject(Subject subject);

    @Insert
    void insertNewSessionSchedule(SessionSchedule sessionSchedule);

    @Insert
    void insertNewClassSession(ClassSession classSession);

    @Insert
    void insertNewSemesterDate(SemesterDate semesterDate);

    @Update
    void updateUserCredential(User user);

    @Update
    void updateSubject(Subject subject);

    @Update
    void updateSessionSchedule(SessionSchedule sessionSchedule);

    @Update
    void updateClassSession(ClassSession classSession);

    @Update
    void updateSemesterDate(SemesterDate semesterDate);

    @Delete
    void deleteSubject(Subject subject);

    @Delete
    void deleteSessionSchedule(SessionSchedule sessionSchedule);

    @Delete
    void deleteClassSession(ClassSession classSession);

    @Query("SELECT * FROM User_credentials")
    LiveData<User> getUserCredential();

    @Query("SELECT * FROM Subject")
    LiveData<Subject> getSubject();

    @Query("SELECT * FROM Session_schedule")
    LiveData<SessionSchedule> getSessionSchedule();

    @Query("SELECT * FROM Class_session")
    LiveData<ClassSession> getClassSession();

    @Query("SELECT * FROM Semester_date")
    LiveData<SemesterDate> getSemesterDate();

}
