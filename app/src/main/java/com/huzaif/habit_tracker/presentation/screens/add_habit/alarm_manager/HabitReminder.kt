package com.huzaif.habit_tracker.presentation.screens.add_habit.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar
import java.util.Date

fun setUpPeriodicAlarm(context: Context, reminder: Long, habitName: String) {

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("Time", reminder.toInt())
        putExtra("Name", habitName)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context, reminder.toInt(),
        intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    Log.d("Worker", "Periodic Alarm: --$alarmManager--alarm set for $habitName at ${Date(Calendar.getInstance().timeInMillis)} - for time - ${Date(reminder)}")
    try {
        val interval = AlarmManager.INTERVAL_DAY
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            reminder,
            interval,
            pendingIntent
        )
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}