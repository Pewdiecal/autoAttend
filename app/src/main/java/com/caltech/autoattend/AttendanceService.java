package com.caltech.autoattend;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AttendanceService extends LifecycleService {

    private final IBinder myBinder = new LocalBinder();
    private Calendar calendar;
    private DataRepo dataRepo;
    private AlarmManager alarmManager;
    private int attemptCount;
    private int notiCount;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        dataRepo = new DataRepo(getApplication());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1", "Default profile", importance);
            channel.enableLights(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        if (intent.getAction() != null) {
            if (intent.getAction().equals("Sign In Attendance")) {
                signInAttendance(intent.getStringExtra("Attendance Link"), intent.getIntExtra("Sub ID", -1),
                        intent.getStringExtra("Sub Name"));
            }
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            String sysDate = df.format(new Date());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String semesterDate = prefs.getString("Semester Date End", null);
            if (semesterDate != null) {
                //TODO: check start date as well
                if (!checkDate(sysDate, semesterDate)) {
                    Intent setDateIntent = new Intent(this, SplashActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, setDateIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AttendanceService.this, "1")
                            .setSmallIcon(R.drawable.ic_round_warning_24)
                            .setContentTitle("End of semester")
                            .setContentText("Tap to fix")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Tap to set or extend your semester date, else the automated attendance " +
                                            "sign in feature will not work."))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AttendanceService.this);
                    notificationManager.notify(0, builder.build());
                } else {
                    setWeeklyNewAlarm();
                    dataRepo.getAllSubject().observe(AttendanceService.this, new Observer<List<Subject>>() {
                        @Override
                        public void onChanged(List<Subject> subjects) {
                            if (subjects.size() != 0) {
                                for (Subject subject : subjects) {
                                    setNewAlarm(subject.sub_id, subject.sub_name, subject.session_day, subject.session_time_start, subject.session_link);
                                }
                                stopSelf();
                            }
                        }
                    });
                }
            }

        }
        return START_NOT_STICKY;
    }

    private void setNewAlarm(int subID, String subName, String day, String timeStart, String attendanceLink) {
        switch (day) {
            case "Monday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case "Tuesday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case "Wednesday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case "Thursday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case "Friday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
        }
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStart.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeStart.substring(3)));
        if (!(calendar.getTimeInMillis() < System.currentTimeMillis())) {
            Intent intent = new Intent(this, ServiceBroadcastReceiver.class);
            intent.putExtra("Sub ID", subID);
            intent.putExtra("Attendance ID", attendanceLink);
            intent.putExtra("Sub Name", subName);
            intent.setAction("Trigger Alarm");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, subID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }

    private void setWeeklyNewAlarm() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i = calendar.get(Calendar.WEEK_OF_MONTH);
        calendar.set(Calendar.WEEK_OF_MONTH, ++i);
        Intent intent = new Intent(this, ServiceBroadcastReceiver.class);
        intent.setAction(null);
        final int PENDING_INTENT_ID = 9024;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, PENDING_INTENT_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void removeSession(int subID) {
        Intent intent = new Intent(this, ServiceBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, subID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void signInAttendance(String attendanceLink, int id, String subName) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFailSafeEnabled = prefs.getBoolean("FailSafe status", true);
        int intervalSec = prefs.getInt("FailSafe timeInterval", 60);
        Timer timer = new Timer();
        LiveData<User> subs = dataRepo.getUser();

        subs.observe(this, new Observer<User>() {

            @Override
            public void onChanged(User user) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attendanceLink));
                PendingIntent pendingIntent = PendingIntent.getActivity(AttendanceService.this, 0, browserIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                HttpRequestHandler httpRequestHandler = new HttpRequestHandler(attendanceLink, user.student_id, user.password, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        if (attemptCount == 4) {

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AttendanceService.this, "1")
                                    .setSmallIcon(R.drawable.ic_round_warning_24)
                                    .setContentTitle(subName)
                                    .setContentText("Attendance sign in failure. Tap here to sign in manually.")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AttendanceService.this);
                            notificationManager.notify(0, builder.build());
                            stopSelf();
                        } else {
                            if (isFailSafeEnabled) {
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        timer.cancel();
                                        signInAttendance(attendanceLink, id, subName);
                                        attemptCount++;
                                    }
                                }, intervalSec * 1000, intervalSec * 1000);
                            }
                        }

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        timer.cancel();
                        Document document = Jsoup.parse(response.body().string());
                        Elements redAlert = document.getElementsByClass("alert alert-danger");
                        Elements success = document.getElementsByClass("alert alert-success");
                        SimpleDateFormat dateFormat;
                        SimpleDateFormat timeFormat;

                        if (!redAlert.isEmpty()) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AttendanceService.this, "1")
                                    .setSmallIcon(R.drawable.ic_round_warning_24)
                                    .setContentTitle(subName)
                                    .setContentText("Sign in failed. Tap here to sign in manually.")
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText(redAlert.text()))
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AttendanceService.this);
                            notificationManager.notify(++notiCount, builder.build());

                        } else if (!success.isEmpty()) {

                            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                            timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);
                            dataRepo.updateSignInTime(id, dateFormat.format(new Date()), timeFormat.format(new Date()));
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AttendanceService.this, "1")
                                    .setSmallIcon(R.drawable.ic_round_done_24)
                                    .setContentTitle(subName)
                                    .setContentText("Sign in success")
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText(success.text()))
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AttendanceService.this);
                            notificationManager.notify(++notiCount, builder.build());

                        }
                        stopSelf();
                    }
                });
                httpRequestHandler.submitForm();
            }
        });
    }

    private boolean checkDate(String strDateStart, String strDateEnd) {
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public class LocalBinder extends Binder {
        AttendanceService getService() {
            return AttendanceService.this;
        }
    }

}
