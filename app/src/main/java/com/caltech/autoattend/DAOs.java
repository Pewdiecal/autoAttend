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
    void insertNewSemesterDate(SemesterDate semesterDate);

    @Update
    void updateUserCredential(User user);

    @Update
    void updateSubject(Subject subject);

    @Update
    void updateSemesterDate(SemesterDate semesterDate);

    @Delete
    void deleteSession(Subject subject);

    @Query("DELETE FROM Subject WHERE sub_name = :sub_name")
    void deleteSubject(String sub_name);

    @Query("SELECT * FROM User_credentials")
    LiveData<User> getUserCredential();

    @Query("SELECT * FROM Subject WHERE sub_name = :sub_name")
    LiveData<List<Subject>> getAllSession(String sub_name);

    @Query("SELECT * FROM Subject")
    LiveData<List<Subject>> getAllSubject();

    @Query("SELECT * FROM Semester_date")
    LiveData<SemesterDate> getSemesterDate();

    @Query("SELECT * FROM Subject WHERE session_id = :session_id")
    LiveData<Subject> getSingleSession(String session_id);

    @Query("SELECT * FROM Subject WHERE (sub_name = :sub_name AND class_session = :class_session " +
            "AND session_day = :session_day AND session_time_start = :session_time_start AND session_time_end = :session_time_end)")
    LiveData<Subject> checkSingleSession(String class_session, String session_day, String session_time_start, String session_time_end, String sub_name);

    @Query("SELECT * FROM Subject WHERE sub_name = :sub_name")
    LiveData<List<Subject>> checkSubject(String sub_name);

    @Query("UPDATE Subject SET sub_name = :sub_name, colorHex = :colorHex WHERE sub_name = :og_sub_name")
    void updateSubject(String sub_name, String colorHex, String og_sub_name);

    @Query("UPDATE Subject SET last_signIn_date = :date, last_signIn_time = :time WHERE sub_id = :id")
    void updateSignInTime(int id, String date, String time);

}
