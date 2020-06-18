package com.caltech.autoattend;

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
    void insertNewSubject(Subject subject, SessionSchedule sessionSchedule, ClassSession classSession);

    @Update
    void updateUserCredential(User user);

    @Update
    void updateSubject(Subject subject, SessionSchedule sessionSchedule, ClassSession classSession);

    @Delete
    void deleteSubject(Subject subject);

    @Query("SELECT student_id FROM user_credentials WHERE student_id = '123456'")
    User getUserCredential();

    @Query("DELETE FROM user_credentials")
    void nukeTableUserCredentials();

    @Query("DELETE FROM session_schedule")
    public void nukeTableSessionSchedule();

    @Query("DELETE FROM subject")
    public void nukeTableSubject();

    @Query("DELETE FROM class_session")
    public void nukeTableClassSession();
}
