package com.huzaif.habit_tracker.presentation.screens.habit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huzaif.habit_tracker.data.local.HabitWithCompletions
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarRow(modifier: Modifier = Modifier, weekState: WeekCalendarState, habit: HabitWithCompletions, onDateClick: (LocalDate) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        WeekCalendar(
            state = weekState,
            dayContent = { WeekDay(it, habit, onDateClick) },
            weekHeader = { WeekHeader(week = it) },
            userScrollEnabled = false
        )
    }
}

@Composable
fun WeekDay(dayState: WeekDay, habit: HabitWithCompletions, onDateClick: (LocalDate) -> Unit) {
    val date: LocalDate = dayState.date

    val currentDate = LocalDate.now()
    val statusOfCurrentDate = habit.completions.find { it.epochDay == dayState.date.toEpochDay() }
    val isDateInRange =
        date.toEpochDay() >= habit.habit.startDateEpochDay && (habit.habit.endDateEpochDay?.let { it >= date.toEpochDay() } != false)
    val isDateClickable = isDateInRange && date <= currentDate
    val boxColor: Color =
        if (isDateInRange) {
            if (statusOfCurrentDate==null && date<currentDate){
                MaterialTheme.colorScheme.tertiary
            }else {
                when (statusOfCurrentDate?.isComplete) {
                    true -> MaterialTheme.colorScheme.secondary
                    false -> MaterialTheme.colorScheme.tertiary
                    else -> Color.DarkGray
                }
            }
        } else {
            Color.Transparent
        }
    val borderColor: Color =
        if (date == currentDate && statusOfCurrentDate?.isComplete == null && isDateClickable) MaterialTheme.colorScheme.onBackground else boxColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isDateClickable) {
                onDateClick(date)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .border(1.dp, borderColor, shape = RoundedCornerShape(10.dp))
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