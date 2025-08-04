package com.huzaif.habit_tracker.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.huzaif.habit_tracker.presentation.navigation.component.AppBottomBar
import com.huzaif.habit_tracker.presentation.screens.add_habit.AddEditHabitScreen
import com.huzaif.habit_tracker.presentation.screens.habit.HabitScreen
import com.huzaif.habit_tracker.presentation.screens.habit_detail.HabitDetailScreen
import com.huzaif.habit_tracker.presentation.screens.settings.SettingsScreen
import com.huzaif.habit_tracker.presentation.screens.today.TodayScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val listOfScreensWithBottomBar = listOf(
        Screen.TodayScreen.route,
        Screen.HabitScreen.route,
        Screen.SettingsScreen.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in listOfScreensWithBottomBar)
                AppBottomBar(
                    modifier = Modifier,
                    navController = navController
                )
        }
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

            composable(Screen.AddHabitScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                    }
                )
            ) {
                val id = it.arguments?.getLong("id") ?: 0L
                AddEditHabitScreen(
                    modifier = modifier,
                    navController = navController,
                    id = id
                )
            }

            composable(Screen.HabitScreen.route) {
                HabitScreen(
                    modifier = modifier,
                    navController = navController
                )
            }

            composable(
                Screen.HabitDetailScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                    }
                )
            ) {
                val id = it.arguments?.getLong("id") ?: 0L
                HabitDetailScreen(
                    modifier = modifier,
                    navController = navController,
                    id = id
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