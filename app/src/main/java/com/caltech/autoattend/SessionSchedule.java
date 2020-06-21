package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Session_schedule", primaryKeys = "session_id")
public class SessionSchedule {
    @NonNull
    public String session_id;

    public String session_time;
    public String session_day;
    public String session_link;
}
