package com.huzaif.habit_tracker.presentation.screens.add_habit.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.Calendar
import java.util.Date

fun setUpPeriodicReminder(context: Context, reminder: Long, habitName: String, habitId: Long) {

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("habitName", habitName)
        putExtra("habitId", habitId)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context, habitId.toInt(),
        intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    Log.d(
        "Worker",
        "Periodic Alarm: --$alarmManager--alarm set for $habitName at ${Date(Calendar.getInstance().timeInMillis)} - for time - ${
            Date(reminder)
        }"
    )
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
        Toast.makeText(context, "Failed to set reminder", Toast.LENGTH_SHORT).show()
    }
}

fun cancelPeriodicReminder(context: Context, habitName: String, habitId: Long) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("Name", habitName)
        putExtra("habitId", habitId)
    }

    Log.d("Worker", "reminder id $habitId and name $habitName canceled ")
    val pendingIntent = PendingIntent.getBroadcast(
        context, habitId.toInt(),
        intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    try {
        alarmManager.cancel(pendingIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}