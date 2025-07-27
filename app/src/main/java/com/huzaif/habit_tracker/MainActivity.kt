package com.huzaif.habit_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.huzaif.habit_tracker.presentation.screens.today.TodayScreen
import com.huzaif.habit_tracker.ui.theme.Habit_TrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Habit_TrackerTheme {
                Surface {
                    TodayScreen(modifier = Modifier)
                }
            }
        }
    }
}