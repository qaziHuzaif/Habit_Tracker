package com.huzaif.habit_tracker.presentation.screens.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TodayScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now().toEpochDay())
    private val _todayHabits = MutableStateFlow<List<DayHabit>>(emptyList())
    val todayHabits: StateFlow<List<DayHabit>> = _todayHabits

    init {
        // Collect the DAO flow, but automatically switch whenever _selectedDate emits a new epochDay
        viewModelScope.launch {
            _selectedDate
                .flatMapLatest { epochDay ->
                    habitRepository.getHabitsWithCompletionOnDate(epochDay)
                }
                .collect { list ->
                    _todayHabits.value = list.map { it ->
                        DayHabit(
                            id = it.habit.id,
                            name = it.habit.name,
                            isCompleted = it.isComplete
                        )
                    }
                }
        }
    }

    /// Call this from your UI when the user taps a new date
    fun setSelectedDate(epochDay: Long) {
        _selectedDate.value = epochDay
    }

    fun markTodayCompletion(habitId: Long, isComplete: Boolean, date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.markHabitCompletion(
                CompletionEntity(habitId, date, isComplete)
            )
        }
    }
}
