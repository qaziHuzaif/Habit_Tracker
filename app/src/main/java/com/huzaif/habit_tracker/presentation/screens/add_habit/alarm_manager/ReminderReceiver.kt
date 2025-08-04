package com.huzaif.habit_tracker.presentation.screens.add_habit.alarm_manager

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.huzaif.habit_tracker.MainActivity
import com.huzaif.habit_tracker.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

private const val CHANNEL = "Habit"

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val currentTime = System.currentTimeMillis()
        val longToDateTime: Date = Date(currentTime)
        val reminderTime = intent?.getLongExtra("Time", 0L) ?: 0L
        val title = intent?.getStringExtra("Name") ?: "NA"

        val interval = 24 * 60 * 60 * 1000

//        Log.d("Worker", "Reminder Receiver: started at (T- $id name- $title at ${System.currentTimeMillis()} ie. $longToDateTime)")
        Log.d("Worker", "Reminder Receiver: --alarm set for $title triggered at ${Date(Calendar.getInstance().timeInMillis)} \n\t next trigger at ${Date(Calendar.getInstance().timeInMillis + interval)} ")

        if (ContextCompat.checkSelfPermission(
                context!!,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val notificationManager =
                ContextCompat.getSystemService(context, NotificationManager::class.java)

            val channel = NotificationChannel(
                CHANNEL,
                "CHANNEL",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager?.createNotificationChannel(channel)

            Log.d("Worker", "Reminder Receiver: executed")

            val reminderIntent = Intent(context, MainActivity::class.java)
            val reminderPendingIntent = PendingIntent.getActivity(
                context,
                reminderTime.toInt(),
                reminderIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val reminderNotification = Notification.Builder(context, CHANNEL)
                .setContentTitle(title)
                .setContentText("(now - ${longToDateTime}, for - ${Date(reminderTime)})")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(reminderPendingIntent)
                .build()

            notificationManager?.notify(reminderTime.toInt(), reminderNotification)

        }
    }
}