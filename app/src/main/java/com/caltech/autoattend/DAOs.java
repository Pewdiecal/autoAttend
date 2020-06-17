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

    @Query("SELECT * FROM user_credentials WHERE email = 'test24' ")
    User getAllData();


}
