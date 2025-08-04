package com.huzaif.habit_tracker.presentation.screens.habit_detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Progress(modifier: Modifier = Modifier, progress: Float, strokeWidth: Int = 16) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineMedium
        )
        CircularProgressIndicator(
            progress = {
                progress
            },
            modifier = modifier.size(180.dp),
            color = MaterialTheme.colorScheme.secondary.copy(0.8f),
            trackColor = MaterialTheme.colorScheme.tertiary.copy(0.6f),
            strokeWidth = strokeWidth.dp,
            gapSize = 10.dp
        )
    }
}