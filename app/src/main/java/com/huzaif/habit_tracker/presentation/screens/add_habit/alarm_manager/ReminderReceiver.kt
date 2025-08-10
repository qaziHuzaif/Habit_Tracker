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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val CHANNEL = "Habit"

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val title = intent?.getStringExtra("habitName") ?: "Notification"
        val id = intent?.getLongExtra("habitId", 0) ?: 0
        val endDate: Long? = intent?.getLongExtra("endDate", 0)

        Log.d(
            "Worker",
            "Reminder Receiver: --alarm set for $title triggered at ${
                LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"))
            }"
        )

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
                id.toInt(),
                reminderIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val reminderNotification = Notification.Builder(context, CHANNEL)
                .setContentTitle(title)
                .setContentText("Time to complete the habit!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(reminderPendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager?.notify(id.toInt(), reminderNotification)

        }

        // remove reminder if end date is reached
        if (endDate!=null && endDate!=0L && endDate <= LocalDate.now().toEpochDay()){
            cancelPeriodicReminder(context, title, id)
        }
    }
}