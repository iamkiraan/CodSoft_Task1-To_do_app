package com.example.taskflow.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class MidnightAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Re-schedule the alarm if the device is rebooted
            scheduleMidnightAlarm(context);
        } else {
            // Clear tasks here
            scheduleClearTasks(context);
        }
    }

    private void scheduleClearTasks(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ClearTaskReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Set to trigger next midnight

        // Schedule the alarm to trigger at midnight daily
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }

        Log.d("MidnightAlarmReceiver", "Alarm scheduled for midnight");
    }

    public static void scheduleMidnightAlarm(Context context) {
        MidnightAlarmReceiver alarmReceiver = new MidnightAlarmReceiver();
        alarmReceiver.scheduleClearTasks(context);
    }
}
