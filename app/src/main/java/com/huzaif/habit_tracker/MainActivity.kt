package com.huzaif.habit_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.huzaif.habit_tracker.presentation.navigation.AppNavGraph
import com.huzaif.habit_tracker.ui.theme.Habit_TrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Habit_TrackerTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}