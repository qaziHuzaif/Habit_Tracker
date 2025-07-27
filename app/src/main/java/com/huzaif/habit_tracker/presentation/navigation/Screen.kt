package com.huzaif.habit_tracker.presentation.navigation

sealed class Screen(val route: String) {
    object TodayScreen: Screen("today_screen")
    object AddHabitScreen: Screen("add_habit_screen")
    object HabitScreen: Screen("habit_screen")
    object SettingsScreen: Screen("settings_screen")
}