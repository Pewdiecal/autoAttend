package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "User_credentials", primaryKeys = "email")
public class User {
    @NonNull
    public String email;
    public String password;

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
