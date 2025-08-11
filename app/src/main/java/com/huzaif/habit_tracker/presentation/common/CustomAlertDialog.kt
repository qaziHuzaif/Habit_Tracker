package com.huzaif.habit_tracker.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    confirmationText: String,
    onConfirmation: () -> Unit,
    dismissText: String,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmation()
                    onDismiss()
                }) {
                    Text(confirmationText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.textButtonColors()
                        .copy(contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                ) {
                    Text(dismissText)
                }
            }
        )
    }
}
