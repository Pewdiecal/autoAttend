package com.caltech.autoattend;

import androidx.room.Entity;

@Entity(tableName = "Semester_date", primaryKeys = "primaryKey")
public class SemesterDate {

    public int primaryKey;

    public String startDate;
    public String endDate;

    SemesterDate(int primaryKey, String startDate, String endDate) {
        this.primaryKey = primaryKey;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
