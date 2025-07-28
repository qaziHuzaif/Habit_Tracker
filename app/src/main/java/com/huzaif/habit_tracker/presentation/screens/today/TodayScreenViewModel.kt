package com.huzaif.habit_tracker.presentation.screens.today

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TodayScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _todayHabits = MutableStateFlow<List<DayHabit>>(emptyList())
    val todayHabits: StateFlow<List<DayHabit>> get() = _todayHabits

    init {
        // Dummy list of habits
        val habits =
            List(5) { index ->
                DayHabit(
                    name = "Habit $index",
                    isCompleted = if (index == 1) true else if (index % 2 == 0) false else null
                )
            }

        _todayHabits.value = habits

    }
}
