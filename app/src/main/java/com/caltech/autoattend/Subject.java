package com.caltech.autoattend;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Subject")
public class Subject implements Comparable<Subject> {

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


    @Override
    public int compareTo(Subject o) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm", Locale.UK);
        Date dateStart = null;
        Date nextDate = null;
        Date timeStart = null;
        Date nextTime = null;

        try {
            if (this.last_signIn_date != null && o.last_signIn_date != null) {
                dateStart = df.parse(this.last_signIn_date);
                nextDate = df.parse(o.last_signIn_date);
                timeStart = dt.parse(this.last_signIn_time);
                nextTime = dt.parse(o.last_signIn_time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateStart != null) {
            if ((this.sub_id < o.sub_id) && dateStart.compareTo(nextDate) < 0 && timeStart.compareTo(nextTime) < 0) {
                return -1;
            } else if ((this.sub_id > o.sub_id) && dateStart.compareTo(nextDate) > 0 && timeStart.compareTo(nextTime) > 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return Integer.compare(this.sub_id, o.sub_id);
        }
    }
}
