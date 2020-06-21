package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Class_session", primaryKeys = {"sub_id", "sub_name"})
public class ClassSession {
    @NonNull
    public String sub_id;

    @NonNull
    public String sub_name;

    public String class_session;
}
