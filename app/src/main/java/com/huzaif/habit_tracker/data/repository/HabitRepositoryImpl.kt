package com.huzaif.habit_tracker.data.repository

import com.huzaif.habit_tracker.data.local.HabitDAO
import com.huzaif.habit_tracker.data.local.HabitWithCompletionOnDate
import com.huzaif.habit_tracker.data.local.HabitWithCompletions
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDAO
) : HabitRepository {
    override suspend fun addHabit(habit: HabitEntity) {
        habitDao.upsertHabit(habit)
    }

    override suspend fun editHabit(habit: HabitEntity) {
        habitDao.editHabit(habit)
    }

    override suspend fun markHabitCompletion(completion: CompletionEntity) {
        habitDao.markCompletionAndUpdateStreaks(completion)
    }

    override fun getHabitById(habitId: Long): Flow<HabitEntity> {
        return habitDao.getHabitById(habitId)
    }

    override fun getAllHabitsWithCompletions(): Flow<List<HabitWithCompletions>> {
        return habitDao.getAllHabitsWithCompletions()
    }

    override fun getHabitWithCompletionsById(habitId: Long): Flow<HabitWithCompletions> {
        return habitDao.getHabitWithCompletionsById(habitId)
    }

    override fun getHabitsWithCompletionOnDate(epochDay: Long): Flow<List<HabitWithCompletionOnDate>> {
        return habitDao.getHabitsWithCompletionOnDate(epochDay)
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    override suspend fun resetHabit(habitId: Long) {
        habitDao.resetHabit(habitId)
    }
}