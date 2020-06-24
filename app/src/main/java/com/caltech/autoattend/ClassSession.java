package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Class_session", primaryKeys = {"class_session_id"})
public class ClassSession {

    @NonNull
    public String class_session_id;

    public String class_session;

    public ClassSession(@NonNull String class_session_id, String class_session) {
        this.class_session_id = class_session_id;
        this.class_session = class_session;
    }

}
