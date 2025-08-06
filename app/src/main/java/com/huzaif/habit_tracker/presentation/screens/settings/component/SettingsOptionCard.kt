package com.huzaif.habit_tracker.presentation.screens.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsOptionCard(
    modifier: Modifier,
    settingsOptionName: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 14.dp, vertical = 20.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = settingsOptionName,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
        )
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        thickness = 0.4.dp
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SettingsOptionCardMovieVersePreview() {
    SettingsOptionCard(
        modifier = Modifier,
        settingsOptionName = "Test Option"
    )
}
