package com.huzaif.habit_tracker.presentation.screens.add_habit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huzaif.habit_tracker.data.model.HabitEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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


    suspend fun addHabit(habit: HabitEntity): Long {
        var id = 0L
        viewModelScope.async {
            id = habitRepository.addHabit(habit)
            Log.d("Worker", "addHabit: $id")
        }.await()
        return id
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