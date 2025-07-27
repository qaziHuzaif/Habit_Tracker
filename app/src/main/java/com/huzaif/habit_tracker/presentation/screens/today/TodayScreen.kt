package com.huzaif.habit_tracker.presentation.screens.today

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.screens.today.component.HomeWeekCalendar
import com.huzaif.habit_tracker.ui.theme.White
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun TodayScreen(modifier: Modifier = Modifier) {
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
                onClick = {},
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
                }
            }

            // Content of the selected date
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.secondary)
                    .heightIn(min = 500.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp
                )
                if (selection != currentDate) {
                    TextButton(
                        onClick = {
                            selection = currentDate
                            coroutineScope.launch {
                                homeWeekCalendarState.animateScrollToDate(currentDate)
                            }
                        },
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
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