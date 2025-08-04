package com.huzaif.habit_tracker.presentation.screens.add_habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huzaif.habit_tracker.data.model.HabitEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHabitScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow(
        HabitEntity(
            id = 0,
            name = "",
            description = null,
            startDateEpochDay = 0L,
            endDateEpochDay = null,
            timeAndReminder = null,
            priority = 0
        )
    )
    val habitDetail = _habits


    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            habitRepository.addHabit(habit)
        }
    }

    fun editHabit(habit: HabitEntity) {
        viewModelScope.launch {
            habitRepository.editHabit(habit)
        }
    }

    fun getHabitById(id: Long) {
        viewModelScope.launch {
            habitRepository.getHabitById(id).collect {
                habitDetail.value = it
            }
        }
    }

}