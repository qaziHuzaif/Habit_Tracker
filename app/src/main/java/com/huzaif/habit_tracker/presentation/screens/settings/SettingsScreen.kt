package com.huzaif.habit_tracker.presentation.screens.settings

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.huzaif.habit_tracker.presentation.screens.settings.component.SettingsOptionCard

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopBar(title = "Settings") },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                thickness = 0.4.dp
            )
            // General App Settings
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Appearance")
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Notifications"){
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            }

            //  Privacy & Security
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Privacy Policy")
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Terms of Use")

            // Support & Feedback
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Help and Support")
            SettingsOptionCard(modifier = modifier, settingsOptionName = "Feedback")
            SettingsOptionCard(modifier = modifier, settingsOptionName = "About")


            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "App Version: 1.0.0",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SettingsPreview() {
    SettingsScreen(
        modifier = Modifier,
        navController = rememberNavController()
    )
}
