package com.huzaif.habit_tracker.presentation.screens.habit_detail

data class MonthHabit(
    val habit: Habit = Habit(),
    val completions: List<DayStatus> = emptyList()
)

data class Habit(
    val id: Long = 0,
    val name: String = "",
    val description: String? = null,
    val startDateEpochDay: Long = 0,    // LocalDate.toEpochDay()
    val endDateEpochDay: Long? = null,     // nullable: no end if null
//    val list: List<DayStatus> = emptyList(),
    val frequency: Int = 0,          // e.g. "DAILY - 0", "WEEKLY", "MONTHLY", "CUSTOM - 1"
    val timeAndReminder: Long? = null,     // epoch millis for daily reminder, null = none
    val priority: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0
)

data class DayStatus(
    val epochDay: Long,
    val isComplete: Boolean?
)
