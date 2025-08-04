package com.huzaif.habit_tracker.presentation.screens.habit_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.screens.habit_detail.component.Container
import com.huzaif.habit_tracker.presentation.screens.habit_detail.component.HabitCalendar
import com.huzaif.habit_tracker.presentation.screens.habit_detail.component.Progress
import java.time.LocalDate

@Composable
fun HabitDetailScreen(modifier: Modifier = Modifier, navController: NavHostController, id: Long) {
    val viewModel: HabitDetailScreenViewModel = hiltViewModel()
    val habitDetail by viewModel.habitDetail.collectAsState()

    LaunchedEffect(id) {
        viewModel.getHabitDetail(id)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = habitDetail.habit.name,
                isNavigationIconVisible = true,
                navController = navController
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(vertical = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HabitCalendar(habit = habitDetail) { date ->
                val status = habitDetail.completions.find { it.epochDay == date.toEpochDay() }
                val completionStatus: Boolean = if (status == null) true else !(status.isComplete)!!
                viewModel.markCompletionOnDate(
                    habitId = habitDetail.habit.id,
                    isComplete = completionStatus,
                    date = date.toEpochDay(),
                )
            }

            Container(
                modifier = Modifier,
                title = "Description"
            ) {
                Text(
                    text = habitDetail.habit.description ?: "No description available",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Container(
                modifier = Modifier,
                title = "Current Streak"
            ) {
                Text(
                    text = "${habitDetail.habit.currentStreak} Days",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Container(
                modifier = Modifier,
                title = "Best Streak"
            ) {
                Text(
                    text = "${habitDetail.habit.bestStreak} Days",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            val progress = remember(habitDetail) {
                val totalDays =
                    LocalDate.now().plusDays(1).toEpochDay() - habitDetail.habit.startDateEpochDay
                habitDetail.completions.filter { it.isComplete == true }.size / totalDays.toFloat()
            }

            Container(
                modifier = Modifier,
                title = "Progress"
            ) {
                Progress(modifier = Modifier.size(180.dp), progress = progress)
            }
        }
    }
}