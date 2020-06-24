package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Session_schedule", primaryKeys = "session_id")
public class SessionSchedule {
    @NonNull
    public String session_id;

    public String sub_name;

    public String session_time;
    public String session_day;
    public String session_link;

    public SessionSchedule(@NonNull String session_id, String sub_name, String session_time, String session_day, String session_link) {
        this.session_id = session_id;
        this.sub_name = sub_name;
        this.session_time = session_time;
        this.session_day = session_day;
        this.session_link = session_link;
    }
}
