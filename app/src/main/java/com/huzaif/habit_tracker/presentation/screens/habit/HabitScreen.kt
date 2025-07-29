package com.huzaif.habit_tracker.presentation.screens.habit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.screens.habit.component.HabitCard
import java.time.LocalDate

@Composable
fun HabitScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(title = "Habit") },
//        bottomBar = { AppBottomBar(modifier = Modifier, navController = navController) }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            HabitCard(
                habit = WeekHabit(
                    0,
                    "Habit 1",
                    LocalDate.of(2025, 7, 24).toEpochDay(),
                    LocalDate.of(2025, 7, 28).toEpochDay(),
                    listOf(
                        DayStatus(
                            LocalDate.of(2025, 7, 24).toEpochDay(),
                            true
                        ),
                        DayStatus(
                            LocalDate.of(2025, 7, 26).toEpochDay(),
                            false
                        ),
                        DayStatus(
                            LocalDate.of(2025, 7, 27).toEpochDay(),
                            true
                        )
                    )
                )
            )
        }
    }
}