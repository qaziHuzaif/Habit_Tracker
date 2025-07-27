package com.huzaif.habit_tracker.presentation.screens.today.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import java.time.LocalDate

@Composable
fun HomeWeekCalendar(
    selection: LocalDate,
    homeWeekCalendarState: WeekCalendarState,
    onClick: (LocalDate) -> Unit
) {

    WeekCalendar(
        state = homeWeekCalendarState,
        dayContent = { day ->
            Day(day.date, isSelected = selection == day.date, onClick = onClick)
        },
        modifier = Modifier
            .padding(bottom = 4.dp)
            .background(MaterialTheme.colorScheme.background),
        calendarScrollPaged = false,
    )
}


@Composable
private fun Day(
    date: LocalDate,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val boxColor: Color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor: Color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .width(46.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick(date) }
                .clip(shape = RoundedCornerShape(10.dp))
                .background(boxColor),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = date.dayOfWeek.name.substring(0..2),
                    fontSize = 12.sp,
                    color = textColor,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                )
            }
            if (date == LocalDate.now()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomCenter),
                )
            }

        }
    }
}
