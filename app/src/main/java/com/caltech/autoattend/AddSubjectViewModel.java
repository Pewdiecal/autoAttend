package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSubjectViewModel extends AndroidViewModel {

    private DataRepo dataRepo;
    private ArrayList<CustomColors> customColors = new ArrayList<>();
    private HashMap<String, String> colorHex = new HashMap<>();
    private HashMap<String, String> colorName = new HashMap<>();
    private String[] days;
    private String[] classSessions;

    public AddSubjectViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
        days = new String[]{application.getString(R.string.addSub_monday), application.getString(R.string.addSub_tues),
                application.getString(R.string.addSub_wed), application.getString(R.string.addSub_thu),
                application.getString(R.string.addSub_fri)};

        classSessions = new String[]{application.getString(R.string.addSub_lec), application.getString(R.string.addSub_tut),
                application.getString(R.string.addSub_lab)};

        customColors.add(new CustomColors(application.getString(R.string.addSub_pinkColor), R.drawable.ic_pink));
        customColors.add(new CustomColors(application.getString(R.string.addSub_amberColor), R.drawable.ic_amber));
        customColors.add(new CustomColors(application.getString(R.string.addSub_blueColor), R.drawable.ic_blue));
        customColors.add(new CustomColors(application.getString(R.string.addSub_cyanColor), R.drawable.ic_cyan));
        customColors.add(new CustomColors(application.getString(R.string.addSub_indigoColor), R.drawable.ic_indigo));
        customColors.add(new CustomColors(application.getString(R.string.addSub_orangeColor), R.drawable.ic_orange));
        customColors.add(new CustomColors(application.getString(R.string.addSub_purpleColor), R.drawable.ic_purple));
        customColors.add(new CustomColors(application.getString(R.string.addSub_redColor), R.drawable.ic_red));
        customColors.add(new CustomColors(application.getString(R.string.addSub_tealColor), R.drawable.ic_teal));
        customColors.add(new CustomColors(application.getString(R.string.addSub_blueGreyColor), R.drawable.ic_bluegrey));

        colorHex.put(application.getString(R.string.addSub_pinkColor), application.getString(R.string.pink));
        colorHex.put(application.getString(R.string.addSub_amberColor), application.getString(R.string.amber));
        colorHex.put(application.getString(R.string.addSub_blueColor), application.getString(R.string.blue));
        colorHex.put(application.getString(R.string.addSub_cyanColor), application.getString(R.string.cyan));
        colorHex.put(application.getString(R.string.addSub_indigoColor), application.getString(R.string.indigo));
        colorHex.put(application.getString(R.string.addSub_orangeColor), application.getString(R.string.orange));
        colorHex.put(application.getString(R.string.addSub_purpleColor), application.getString(R.string.purple));
        colorHex.put(application.getString(R.string.addSub_redColor), application.getString(R.string.red));
        colorHex.put(application.getString(R.string.addSub_tealColor), application.getString(R.string.teal));
        colorHex.put(application.getString(R.string.addSub_blueGreyColor), application.getString(R.string.blueGrey));

        colorName.put(application.getString(R.string.pink), application.getString(R.string.addSub_pinkColor));
        colorName.put(application.getString(R.string.amber), application.getString(R.string.addSub_amberColor));
        colorName.put(application.getString(R.string.blue), application.getString(R.string.addSub_blueColor));
        colorName.put(application.getString(R.string.cyan), application.getString(R.string.addSub_cyanColor));
        colorName.put(application.getString(R.string.indigo), application.getString(R.string.addSub_indigoColor));
        colorName.put(application.getString(R.string.orange), application.getString(R.string.addSub_orangeColor));
        colorName.put(application.getString(R.string.purple), application.getString(R.string.addSub_purpleColor));
        colorName.put(application.getString(R.string.red), application.getString(R.string.addSub_redColor));
        colorName.put(application.getString(R.string.teal), application.getString(R.string.addSub_tealColor));
        colorName.put(application.getString(R.string.blueGrey), application.getString(R.string.addSub_blueGreyColor));
    }

    public void insertNewSubject(String sub_name, String color, String session_id, String class_session,
                                 String session_time_start, String session_time_end, String session_day, String session_link) {

        dataRepo.insertNewSubject(new Subject(sub_name, color, session_id, class_session,
                session_time_start, session_time_end, session_day, session_link, null, null));
    }

    public void updateSubject(Subject subject, String session_id, String class_session,
                              String session_time_start, String session_time_end, String session_day, String color, String session_link) {

        subject.session_id = session_id;
        subject.class_session = class_session;
        subject.session_time_start = session_time_start;
        subject.session_time_end = session_time_end;
        subject.session_day = session_day;
        subject.colorHex = color;
        subject.session_link = session_link;

        dataRepo.updateSubject(subject);
    }

    public void updateSubject(String sub_name, String colorHex, String og_sub_name) {
        dataRepo.updateSubject(sub_name, colorHex, og_sub_name);
    }

    public LiveData<Subject> getSingleSession(String session_id) {
        return dataRepo.getSingleSession(session_id);
    }

    public LiveData<List<Subject>> checkSubject(String subject) {
        return dataRepo.checkSubject(subject);
    }

    public LiveData<Subject> checkSingleSession(String class_session, String session_day, String session_time_start, String session_time_end, String sub_name) {
        return dataRepo.checkSingleSession(class_session, session_day, session_time_start, session_time_end, sub_name);
    }

    public LiveData<List<Subject>> getSubject(String sub_name) {
        return dataRepo.getAllSession(sub_name);
    }

    public String[] getDays() {
        return days;
    }

    public String[] getClassSessions() {
        return classSessions;
    }

    public ArrayList<CustomColors> getCustomColors() {
        return customColors;
    }

    public HashMap<String, String> getColorHex() {
        return colorHex;
    }

    public HashMap<String, String> getColorName() {
        return colorName;
    }
}
