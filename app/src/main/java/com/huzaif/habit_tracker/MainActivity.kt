package com.huzaif.habit_tracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.huzaif.habit_tracker.presentation.navigation.AppNavGraph
import com.huzaif.habit_tracker.ui.theme.Habit_TrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS,android.Manifest.permission.USE_EXACT_ALARM), 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SCHEDULE_EXACT_ALARM), 0)
        }

//        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SET_ALARM, android.Manifest.permission.WAKE_LOCK, android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS), 0)

        setContent {
            val navController = rememberNavController()
            Habit_TrackerTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}