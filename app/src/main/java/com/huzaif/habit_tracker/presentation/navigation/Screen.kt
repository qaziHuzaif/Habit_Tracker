package com.huzaif.habit_tracker.presentation.navigation

sealed class Screen(val route: String) {
    data object TodayScreen: Screen("today_screen")
    data object AddHabitScreen: Screen("add_habit_screen")
    data object HabitScreen: Screen("habit_screen")
    data object HabitDetailScreen: Screen("habit_detail_screen")
    data object SettingsScreen: Screen("settings_screen")
}