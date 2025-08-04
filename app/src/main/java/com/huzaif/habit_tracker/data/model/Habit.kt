package com.huzaif.habit_tracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String?,
    val startDateEpochDay: Long,    // LocalDate.toEpochDay()
    val endDateEpochDay: Long?,     // nullable: no end if null
    val frequency: Int = 0,          // e.g. "DAILY - 0", "WEEKLY", "MONTHLY", "CUSTOM - 1"
    val timeAndReminder: Long?,     // epoch millis for daily reminder, null = none
    val priority: Int,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0
)