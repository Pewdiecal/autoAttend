package com.caltech.autoattend;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

    @Query("SELECT * FROM Subject WHERE sub_name = :sub_name")
    LiveData<Subject> getSubject(String sub_name);

    @Query("SELECT * FROM Subject")
    LiveData<List<Subject>> getAllSubject();

    @Query("SELECT * FROM Session_schedule WHERE session_id = :session_id OR sub_name = :sub_name")
    LiveData<List<SessionSchedule>> getSessionSchedule(String session_id, String sub_name);

    @Query("SELECT * FROM Class_session WHERE class_session_id = :class_session_id")
    LiveData<ClassSession> getClassSession(String class_session_id);

    @Query("SELECT * FROM Semester_date")
    LiveData<SemesterDate> getSemesterDate();

}
