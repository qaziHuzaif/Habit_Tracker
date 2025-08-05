package com.huzaif.habit_tracker.domain.repository

import com.huzaif.habit_tracker.data.local.HabitWithCompletionOnDate
import com.huzaif.habit_tracker.data.local.HabitWithCompletions
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity): Long
    suspend fun editHabit(habit: HabitEntity)
    suspend fun markHabitCompletion(completion: CompletionEntity)

    fun getHabitById(habitId: Long): Flow<HabitEntity>
    fun getAllHabitsWithCompletions(): Flow<List<HabitWithCompletions>>
    fun getHabitWithCompletionsById(habitId: Long): Flow<HabitWithCompletions>
    fun getHabitsWithCompletionOnDate(epochDay: Long): Flow<List<HabitWithCompletionOnDate>>

    suspend fun deleteHabit(habit: HabitEntity)
    suspend fun resetHabit(habitId: Long)
}