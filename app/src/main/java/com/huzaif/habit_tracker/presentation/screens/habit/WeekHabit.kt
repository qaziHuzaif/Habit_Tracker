package com.huzaif.habit_tracker.presentation.screens.habit

data class WeekHabit(
    val id: Long,
    val name: String,
    val startDateEpochDay: Long,    // LocalDate.toEpochDay()
    val endDateEpochDay: Long?,     // nullable: no end if null
    val list: List<DayStatus>
)

data class DayStatus(
    val epochDay: Long,
    val isComplete: Boolean?
)