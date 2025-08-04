package com.huzaif.habit_tracker.presentation.screens.today

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.navigation.Screen
import com.huzaif.habit_tracker.presentation.screens.today.component.CurrentDayProgress
import com.huzaif.habit_tracker.presentation.screens.today.component.DayHabitElement
import com.huzaif.habit_tracker.presentation.screens.today.component.HomeWeekCalendar
import com.huzaif.habit_tracker.ui.theme.White
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun TodayScreen(modifier: Modifier, navController: NavHostController) {
    val viewModel: TodayScreenViewModel = hiltViewModel()
    val todayHabits by viewModel.todayHabits.collectAsState()

    val currentDate: LocalDate = remember { LocalDate.now() }
    var selection: LocalDate by rememberSaveable { mutableStateOf(currentDate) }
    val title by remember {
        derivedStateOf {
            if (selection != currentDate) selection.format(DateTimeFormatter.ofPattern("d MMMM y")) else "Today"
        }
    }

    val homeWeekCalendarState =
        rememberWeekCalendarState(
            startDate = YearMonth.now().atStartOfMonth().minusYears(4),
            endDate = YearMonth.now().atStartOfMonth().plusYears(4)
        )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBar(title = title) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddHabitScreen.route + "/0L")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Calendar row to select date on Today Screen
            HomeWeekCalendar(selection, homeWeekCalendarState) { clicked ->
                if (selection != clicked) {
                    selection = clicked
                    viewModel.setSelectedDate(clicked.toEpochDay())
                }
            }

            // Content of the selected date
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (todayHabits.isEmpty()) {
                    Text(
                        text = "No habits today",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = FontStyle.Italic,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            CurrentDayProgress(todayHabits)
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                thickness = 0.4.dp
                            )
                        }
                        items(todayHabits) { habit ->
                            DayHabitElement(
                                habit = habit,
                                isDisabled = selection > currentDate,
                                onClick = {
                                    val status: Boolean = if (habit.isCompleted == null) {
                                        true
                                    } else {
                                        !habit.isCompleted
                                    }
                                    viewModel.markTodayCompletion(
                                        habit.id,
                                        status,
                                        selection.toEpochDay()
                                    )

                                }
                            )
                        }
                        item {
                            Spacer(Modifier.height(100.dp))
                        }
                    }
                }

                // scroll to current date
                if (selection != currentDate) {
                    TextButton(
                        onClick = {
                            selection = currentDate
                            coroutineScope.launch {
                                homeWeekCalendarState.animateScrollToDate(currentDate)
                                viewModel.setSelectedDate(selection.toEpochDay())

                            }
                        },
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            contentColor = White
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(18.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Today",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "Go to Today"
                        )
                    }
                }
            }
        }

    }
}