package com.caltech.autoattend;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Subject")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    public int sub_id;

    public String sub_name;

    public String class_session_id;

    public String session_id;

    public String color;


    public Subject(String sub_name, String color, String session_id, String class_session_id) {
        this.sub_name = sub_name;
        this.color = color;
        this.session_id = session_id;
        this.class_session_id = class_session_id;
    }
}
