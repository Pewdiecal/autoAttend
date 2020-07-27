package com.caltech.autoattend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals("Trigger Alarm")) {
                Intent serviceIntent = new Intent(context, AttendanceService.class);
                serviceIntent.putExtra("Sub ID", intent.getIntExtra("Sub ID", -1));
                serviceIntent.putExtra("Attendance Link", intent.getStringExtra("Attendance ID"));
                serviceIntent.putExtra("Sub Name", intent.getStringExtra("Sub Name"));
                serviceIntent.setAction("Sign In Attendance");
                context.startService(serviceIntent);
            } else {
                Intent serviceIntent = new Intent(context, AttendanceService.class);
                context.startService(serviceIntent);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                        .setSmallIcon(R.drawable.ic_round_warning_24)
                        .setContentTitle("autoAttend")
                        .setContentText("SERVICE STARTUP")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Automated attendance service started"))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(0, builder.build());
            }
        }

    }
}
