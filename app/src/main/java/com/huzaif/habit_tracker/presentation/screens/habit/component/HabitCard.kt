package com.huzaif.habit_tracker.presentation.screens.habit.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huzaif.habit_tracker.presentation.screens.habit.WeekHabit
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun HabitCard(
    modifier: Modifier = Modifier,
    habit: WeekHabit
) {
    val openBottomSheet = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Top Row: Title and Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { openBottomSheet.value = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            CalendarRow(modifier= Modifier,habit)

            Spacer(modifier = Modifier.height(10.dp))

        }
    }

    if (openBottomSheet.value) {
        HabitOptionsBottomSheet(
            onDismiss = { openBottomSheet.value = false },
            onEditClick = {
//                onEditClick()
                openBottomSheet.value = false
            },
            onDeleteClick = {
//                onDeleteClick()
                openBottomSheet.value = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitOptionsBottomSheet(
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Options",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ListItem(
                headlineContent = { Text("Edit") },
                leadingContent = { Icon(Icons.Default.Edit, contentDescription = null) },
                modifier = Modifier.clickable { onEditClick() }
            )
            ListItem(
                headlineContent = { Text("Delete") },
                leadingContent = { Icon(Icons.Default.Delete, contentDescription = null) },
                modifier = Modifier.clickable { onDeleteClick() }
            )
        }
    }
}

@Composable
fun CalendarRow(modifier: Modifier = Modifier, habit: WeekHabit) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val weekState = rememberWeekCalendarState()
        WeekCalendar(
            state = weekState,
            dayContent = { WeekDay(it, habit) },
            weekHeader = { WeekHeader(week = it) },
            userScrollEnabled = false
        )
    }
}

@Composable
fun WeekDay(dayState: WeekDay, habit: WeekHabit) {
    val date: LocalDate = dayState.date

    val currentDate = LocalDate.now()
    val statusOfCurrentDate = habit.list.find { it.epochDay == dayState.date.toEpochDay() }
    val isDateInRange = date.toEpochDay() >= habit.startDateEpochDay && (habit.endDateEpochDay?.let { it >= date.toEpochDay() } != false)
    val isDateClickable = isDateInRange && date<=currentDate
    val boxColor: Color =
        if (isDateInRange)  {
            when (statusOfCurrentDate?.isComplete) {
                true -> MaterialTheme.colorScheme.secondary
                false -> MaterialTheme.colorScheme.tertiary
                else -> Color.DarkGray
            }
        }else{
            Color.Transparent
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isDateClickable) {
                Log.d("Date", "HabitCard: $date")
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .border(1.dp, boxColor, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(boxColor.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun WeekHeader(week: Week) {
    val daysOfWeek: List<DayOfWeek> = week.days.toList().map { it.date.dayOfWeek }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 4.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.name.substring(0..2),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .weight(1f),
                )
            }
        }
    }
}