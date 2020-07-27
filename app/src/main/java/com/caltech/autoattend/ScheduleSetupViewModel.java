package com.caltech.autoattend;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleSetupViewModel extends AndroidViewModel {

    private DataRepo dataRepo;

    public ScheduleSetupViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
    }

    public boolean checkDate(String strDateStart, String strDateEnd) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        Date dateStart = null;
        Date dateEnd = null;
        Date dateSystem = null;
        try {
            dateStart = df.parse(strDateStart);
            dateEnd = df.parse(strDateEnd);
            dateSystem = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return !dateEnd.before(dateStart) && !dateEnd.before(dateSystem);
    }

    public void insertNewSemesterDate(String strDateStart, String strDateEnd) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Semester Date Start", strDateStart);
        editor.putString("Semester Date End", strDateEnd);
        editor.commit();
    }

}
