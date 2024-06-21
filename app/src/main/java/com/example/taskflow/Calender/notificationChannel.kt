package com.example.taskflow.Calender

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class notificationChannel:Application() {
    override fun onCreate() {
        super.onCreate()
        notification()
    }

    private fun notification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                notificationService.COUNTER_CHANNEL_ID,
                "counter",
                NotificationManager.IMPORTANCE_DEFAULT

            )
            channel.description = "event check notification"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }
}