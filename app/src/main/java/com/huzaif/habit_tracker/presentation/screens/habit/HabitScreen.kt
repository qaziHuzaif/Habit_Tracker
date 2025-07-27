package com.huzaif.habit_tracker.presentation.screens.habit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.huzaif.habit_tracker.presentation.common.TopBar

@Composable
fun HabitScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(title = "Habit") },
//        bottomBar = { AppBottomBar(modifier = Modifier, navController = navController) }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding))
    }
}