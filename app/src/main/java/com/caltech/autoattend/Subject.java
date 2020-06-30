package com.caltech.autoattend;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Subject")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    public int sub_id;

    public String sub_name;

    public String class_session;

    public String colorHex;

    public String session_id;

    public String session_time_start;

    public String session_time_end;

    public String session_day;

    public String session_link;

    public String last_signIn_date;

    public String last_signIn_time;


    public Subject(String sub_name, String colorHex, String session_id, String class_session,
                   String session_time_start, String session_time_end, String session_day, String session_link,
                   String last_signIn_date, String last_signIn_time) {

        this.sub_name = sub_name;
        this.colorHex = colorHex;
        this.session_id = session_id;
        this.class_session = class_session;
        this.session_time_start = session_time_start;
        this.session_time_end = session_time_end;
        this.session_day = session_day;
        this.session_link = session_link;
        this.last_signIn_date = last_signIn_date;
        this.last_signIn_time = last_signIn_time;
    }
}
