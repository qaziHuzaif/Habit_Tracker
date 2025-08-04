package com.huzaif.habit_tracker.presentation.screens.today.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huzaif.habit_tracker.presentation.screens.today.DayHabit

@Composable
fun DayHabitElement(
    modifier: Modifier = Modifier,
    habit: DayHabit,
    isDisabled: Boolean,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .clickable(
                    enabled = !isDisabled
                ) {
                    // Handle habit click
                    onClick()
                }
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyLarge
            )
            CustomCheckBox(habit)
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            thickness = 0.4.dp
        )
    }
}

@Composable
fun CustomCheckBox(habit: DayHabit) {
    val color = if (habit.isCompleted == null)
        MaterialTheme.colorScheme.surfaceContainerHigh
    else if (habit.isCompleted)
        MaterialTheme.colorScheme.secondary
    else
        MaterialTheme.colorScheme.tertiary
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(
                color.copy(alpha = 0.2f)
            ),
        contentAlignment = Alignment.Center
    ) {
        habit.isCompleted?.let {
            Icon(
                imageVector = if (habit.isCompleted) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayHabitElementPreview() {
    DayHabitElement(
        habit = DayHabit(id = 0, name = "Morning Run", isCompleted = true),
        isDisabled = false,
        onClick = {

        }
    )
}


