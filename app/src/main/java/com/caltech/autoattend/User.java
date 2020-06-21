package com.caltech.autoattend;

import androidx.room.Entity;

@Entity(tableName = "User_credentials", primaryKeys = "primaryKey")
public class User {

    public int primaryKey;
    public String student_id;
    public String password;

    User(int primaryKey, String student_id, String password) {
        this.primaryKey = primaryKey;
        this.student_id = student_id;
        this.password = password;
    }

}
