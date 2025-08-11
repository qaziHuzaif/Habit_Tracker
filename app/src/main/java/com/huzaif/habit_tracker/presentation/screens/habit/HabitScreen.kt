package com.huzaif.habit_tracker.presentation.screens.habit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.navigation.Screen
import com.huzaif.habit_tracker.presentation.screens.add_habit.alarm_manager.cancelPeriodicReminder
import com.huzaif.habit_tracker.presentation.screens.habit.component.HabitCard
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate

@Composable
fun HabitScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: HabitScreenViewModel = hiltViewModel()
    val habits by viewModel.habits.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar(title = "Habit") }
    ) { innerPadding ->

        if (habits.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No habits yet!",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Italic,
                )
            }
        } else {
            val today = LocalDate.now()
            val weekState: WeekCalendarState = rememberWeekCalendarState(
                firstDayOfWeek = today.minusDays(6).dayOfWeek,
                startDate = today.minusDays(6),
                endDate = today,
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        items = habits,
                        key = { habits -> habits.habit.id }
                    ) { habit ->
                        HabitCard(
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.HabitDetailScreen.route + "/${habit.habit.id}")
                            },
                            weekState = weekState,
                            habit = habit,
                            onEditClick = { navController.navigate(Screen.AddHabitScreen.route + "/${habit.habit.id}") },
                            onResetClick = { viewModel.resetHabit(habit.habit.id) },
                            onDeleteClick = {
                                cancelPeriodicReminder(
                                    context,
                                    habit.habit.name,
                                    habit.habit.id
                                )
                                viewModel.deleteHabit(habit.habit)
                            }
                        ) { date ->
                            val status =
                                habit.completions.find { it.epochDay == date.toEpochDay() }
                            val completionStatus: Boolean =
                                if (status == null) true else !(status.isComplete)
                            viewModel.markCompletionOnDate(
                                habitId = habit.habit.id,
                                isComplete = completionStatus,
                                date = date.toEpochDay()
                            )
                        }
                    }
                }
            }
        }

    }
}