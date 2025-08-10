package com.huzaif.habit_tracker

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.USE_EXACT_ALARM
                ),
                0
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.SCHEDULE_EXACT_ALARM),
                0
            )
        }


        setContent {
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            // State to control the visibility of the AlertDialog
            val showDialog =
                remember { mutableStateOf(!powerManager.isIgnoringBatteryOptimizations(packageName)) }

            Habit_TrackerTheme {
                // Show AlertDialog only if showDialog is true
                RequestAlowBackgroundActivityPermission(showDialog)


                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }

    @Composable
    private fun RequestAlowBackgroundActivityPermission(showDialog: MutableState<Boolean>) {
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showDialog.value = false
                    Toast.makeText(
                        this,
                        "Please allow background activity to receive reminders",
                        Toast.LENGTH_LONG
                    ).show()
                },
                title = { Text(text = "Battery Optimization") },
                text = { Text(text = "To ensure reliable notifications and reminders, please allow background activity for this app. \n\nBattery Usage > Allow background activity") },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                        showDialog.value = false
                    }) {
                        Text("Go to Settings")
                    }
                }
            )
        }
    }
}