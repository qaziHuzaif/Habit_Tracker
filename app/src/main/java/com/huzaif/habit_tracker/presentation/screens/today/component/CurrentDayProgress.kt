package com.huzaif.habit_tracker.presentation.screens.today.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huzaif.habit_tracker.presentation.screens.today.DayHabit

@Composable
fun CurrentDayProgress(todayHabits: List<DayHabit>) {
    Row(
        modifier = Modifier
            .padding(26.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val progress = remember(todayHabits) {
            val completed = todayHabits.filter { it.isCompleted == true }.size
            val total = todayHabits.size
            if (total == 0) 0f else (completed.toFloat() / total.toFloat())
        }
        Column {
            Text(
                text = "Completed",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "${todayHabits.filter { it.isCompleted == true }.size}/${todayHabits.size}",
                fontSize = 46.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.W600
            )
        }
        TodayProgressIndicator(progress = progress)
    }
}

@Composable
private fun TodayProgressIndicator(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${(animatedProgress * 100).toInt()}%",
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.W600
        )
        CircularProgressIndicator(
            progress = {
                animatedProgress
            },
            modifier = Modifier.size(80.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(0.2f),
        )
    }
}