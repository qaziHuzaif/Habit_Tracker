package com.huzaif.habit_tracker.presentation.screens.habit_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huzaif.habit_tracker.presentation.screens.habit_detail.MonthHabit
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun HabitCalendar(
    modifier: Modifier = Modifier,
    habit: MonthHabit,
    onDateClick: (LocalDate) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val monthState = rememberCalendarState(
            startMonth = YearMonth.now().minusYears(2),
            endMonth = YearMonth.now().plusYears(2),
            firstVisibleMonth = YearMonth.now()
        )

        HorizontalCalendar(
            state = monthState,
            dayContent = {
                MonthDay(it, habit, onDateClick)
            },
            monthHeader = {
                MonthHeader(it)
            },
            modifier = Modifier.padding(horizontal = 6.dp)
        )
    }
}

@Composable
fun MonthDay(dayState: CalendarDay, habit: MonthHabit, onDateClick: (LocalDate) -> Unit) {
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
            .aspectRatio(1f)
            .padding(4.dp)
            .border(1.dp, borderColor, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(enabled = isDateClickable) {
                onDateClick(date)
            }
            .background(boxColor.copy(0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = date.dayOfMonth.toString())
    }
}


@Composable
private fun MonthHeader(calendarMonth: CalendarMonth) {
    val daysOfWeek = calendarMonth.weekDays.first().map { it.date.dayOfWeek }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = calendarMonth.yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.name.substring(0..2),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}