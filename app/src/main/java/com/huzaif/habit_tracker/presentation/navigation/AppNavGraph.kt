package com.huzaif.habit_tracker.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.huzaif.habit_tracker.presentation.navigation.component.AppBottomBar
import com.huzaif.habit_tracker.presentation.screens.add_habit.AddHabitScreen
import com.huzaif.habit_tracker.presentation.screens.habit.HabitScreen
import com.huzaif.habit_tracker.presentation.screens.settings.SettingsScreen
import com.huzaif.habit_tracker.presentation.screens.today.TodayScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    Scaffold(
        bottomBar = { AppBottomBar(modifier = Modifier, navController = navController) }
    ) { innerPadding ->
        println(innerPadding)
        NavHost(
            navController = navController,
            startDestination = Screen.TodayScreen.route,
            modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Screen.TodayScreen.route) {
                TodayScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

            composable(Screen.AddHabitScreen.route) {
                AddHabitScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

            composable(Screen.HabitScreen.route) {
                HabitScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

            composable(Screen.SettingsScreen.route) {
                SettingsScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

        }

    }

}