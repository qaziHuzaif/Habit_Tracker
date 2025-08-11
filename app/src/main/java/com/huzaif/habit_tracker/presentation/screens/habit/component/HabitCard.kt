package com.huzaif.habit_tracker.presentation.screens.habit.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.huzaif.habit_tracker.data.local.HabitWithCompletions
import com.huzaif.habit_tracker.presentation.common.CustomAlertDialog
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import java.time.LocalDate

@Composable
fun HabitCard(
    modifier: Modifier = Modifier,
    weekState: WeekCalendarState,
    habit: HabitWithCompletions,
    onEditClick: () -> Unit,
    onResetClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
) {
    val openBottomSheet = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showResetProgressDialog = remember { mutableStateOf(false) }

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
                    text = habit.habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { openBottomSheet.value = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            }

            CalendarRow(
                modifier = Modifier,
                weekState = weekState,
                habit = habit,
                onDateClick = onDateClick
            )

            Spacer(modifier = Modifier.height(10.dp))

        }
    }

    if (openBottomSheet.value) {
        HabitOptionsBottomSheet(
            onDismiss = { openBottomSheet.value = false },
            onEditClick = {
                onEditClick()
                openBottomSheet.value = false
            },
            onResetClick = {
                showResetProgressDialog.value = true
                openBottomSheet.value = false
            },
            onDeleteClick = {
                showDeleteDialog.value = true
                openBottomSheet.value = false
            }
        )
    }

    if (showDeleteDialog.value) {
        CustomAlertDialog(
            showDialog = true,
            onDismiss = { showDeleteDialog.value = false },
            title = "Delete ${habit.habit.name}",
            text = "Are you sure you want to delete this habit?",
            confirmationText = "Delete",
            onConfirmation = { onDeleteClick(); showDeleteDialog.value = false },
            dismissText = "Cancel"
        )
    }
    if (showResetProgressDialog.value) {
        CustomAlertDialog(
            showDialog = true,
            onDismiss = { showResetProgressDialog.value = false },
            title = "Reset Progress",
            text = "Are you sure you want to reset the progress of habit \"${habit.habit.name}\"?",
            confirmationText = "Reset",
            onConfirmation = { onResetClick(); showResetProgressDialog.value = false },
            dismissText = "Cancel"
        )
    }
}