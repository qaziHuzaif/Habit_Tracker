package com.huzaif.habit_tracker.presentation.screens.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huzaif.habit_tracker.data.local.HabitWithCompletions
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<HabitWithCompletions>>(emptyList())
    val habits: StateFlow<List<HabitWithCompletions>> = _habits

    init {
        /*viewModelScope.launch {
            habitDao.getAllHabitsWithCompletions().collect{list ->
                _habits.value = list.map {
                    WeekHabit(
                        id = it.habit.id,
                        name = it.habit.name,
                        startDateEpochDay = it.habit.startDateEpochDay,
                        endDateEpochDay = it.habit.endDateEpochDay,
                        list = it.completions.map {completion->
                            DayStatus(
                                epochDay = completion.epochDay,
                                isComplete = completion.isComplete
                            )
                        }
                    )
                }
            }
        }*/
        viewModelScope.launch {
            habitRepository.getAllHabitsWithCompletions().collect{ list ->
                _habits.value = list
            }
        }
    }


    fun markCompletionOnDate(habitId: Long, isComplete: Boolean, date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.markHabitCompletion(
                CompletionEntity(habitId, date, isComplete)
            )
        }
    }

    fun deleteHabit(habit: HabitEntity){
        viewModelScope.launch {
            habitRepository.deleteHabit(habit = habit)
        }
    }

    fun resetHabit(id: Long){
        viewModelScope.launch {
            habitRepository.resetHabit(habitId = id)
        }
    }

}