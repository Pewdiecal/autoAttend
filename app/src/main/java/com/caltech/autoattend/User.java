package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "User_credentials", primaryKeys = "student_id")
public class User {
    @NonNull
    public String student_id;
    public String password;

    User(String student_id, String password) {
        this.student_id = student_id;
        this.password = password;
    }
}
