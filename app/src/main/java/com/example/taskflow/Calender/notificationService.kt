package com.example.taskflow.Calender

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.taskflow.R
import com.example.taskflow.appBarFragments.CalenderFragment
import java.text.SimpleDateFormat
import java.util.*

class notificationService(private val context: Context) {

    fun showNotification(eventName: String, eventTime: Long) {
        val activityIntent = Intent(context, CalenderFragment::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
        )

        val formattedTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(eventTime))

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.logoit)
            .setContentTitle("Event Created")
            .setContentText("Event '$eventName' created for $formattedTime")
            .setContentIntent(activityPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}
