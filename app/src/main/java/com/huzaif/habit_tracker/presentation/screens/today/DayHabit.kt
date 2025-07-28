package com.huzaif.habit_tracker.presentation.screens.today

data class DayHabit(
    val id: Int = 1,
    val name: String = "Project",
    val priority: Int = 0,
    val isCompleted: Boolean? = null
)