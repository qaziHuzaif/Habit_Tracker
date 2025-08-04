package com.huzaif.habit_tracker.presentation.screens.habit_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitDetailScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _habitDetail = MutableStateFlow(MonthHabit())
    val habitDetail: StateFlow<MonthHabit> = _habitDetail

    fun getHabitDetail(id: Long) {
        viewModelScope.launch {
            habitRepository.getHabitWithCompletionsById(id).collect {
                _habitDetail.value = MonthHabit(
                    habit = Habit(
                        id = it.habit.id,
                        name = it.habit.name,
                        description = it.habit.description,
                        startDateEpochDay = it.habit.startDateEpochDay,
                        endDateEpochDay = it.habit.endDateEpochDay,
                        frequency = it.habit.frequency,
                        timeAndReminder = it.habit.timeAndReminder,
                        priority = it.habit.priority,
                        currentStreak = it.habit.currentStreak,
                        bestStreak = it.habit.bestStreak
                    ),
                    completions = it.completions.map { status ->
                        DayStatus(
                            status.epochDay,
                            status.isComplete
                        )
                    }
                )
            }
        }
    }

    fun markCompletionOnDate(
        habitId: Long,
        isComplete: Boolean,
        date: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.markHabitCompletion(
                CompletionEntity(habitId, date, isComplete)
            )
        }
    }
}