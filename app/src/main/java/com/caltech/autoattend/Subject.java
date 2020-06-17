package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Subject", primaryKeys = {"sub_id", "sub_name"})
public class Subject {

    @NonNull
    public String sub_id;

    @NonNull
    public String sub_name;

    public String session_id;
}
