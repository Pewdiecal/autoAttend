package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleSetupViewModel extends AndroidViewModel {

    private DataRepo dataRepo;
    private LiveData<SemesterDate> semesterDate;

    public ScheduleSetupViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        semesterDate = dataRepo.getSemesterDate();
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
        dataRepo.insertNewSemesterDate(new SemesterDate(1, strDateStart, strDateEnd));
    }

    public void updateSemesterDate(SemesterDate semesterDate) {
        dataRepo.updateSemesterDate(semesterDate);
    }

    public LiveData<SemesterDate> getSemesterDate() {
        return semesterDate;
    }
}
