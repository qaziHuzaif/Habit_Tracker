package com.huzaif.habit_tracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitDAO {

    /* ----------  Create & Update operations  ---------- */

    // 1. Insert or Edit a habit
    @Upsert
    suspend fun upsertHabit(habit: HabitEntity): Long

    // 2. Edit a habit
    @Transaction
    suspend fun editHabit(habit: HabitEntity) {
        deleteCompletionBeforeDate(habitId = habit.id, epochDay = habit.startDateEpochDay)
        habit.endDateEpochDay?.let { deleteCompletionAfterDate(habitId = habit.id, epochDay = it) }
        val (current, best) = calculateStreaks(getCompletionsForHabit(habit.id))
        upsertHabit(habit = habit.copy(bestStreak = best, currentStreak = current))
    }

    // 3. Mark a completion and update streaks
    @Transaction
    suspend fun markCompletionAndUpdateStreaks(completion: CompletionEntity) {
        markCompletion(completion)
        val (current, best) = calculateStreaks(getCompletionsForHabit(completion.habitId))
        updateStreaks(habitId = completion.habitId, currentStreak = current, bestStreak = best)
    }

    // Mark a completion
    @Upsert
    suspend fun markCompletion(completion: CompletionEntity)

    // Update streaks
    @Query("UPDATE habits SET currentStreak = :currentStreak, bestStreak = :bestStreak WHERE id = :habitId")
    suspend fun updateStreaks(habitId: Long, currentStreak: Int, bestStreak: Int)



    /* ----------  Read operations  ---------- */

    // 1. Get a specific habit (without completions)
    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Long): Flow<HabitEntity>

    // 2. Get all habits with their completions
    @Transaction
    @Query("SELECT * FROM habits")
    fun getAllHabitsWithCompletions(): Flow<List<HabitWithCompletions>>


    // 3. Get all habits on a specific date along with their completion status
    @Transaction
    @Query(
        """
        SELECT h.*, c.isComplete 
        FROM habits AS h
        LEFT JOIN completions AS c
        ON h.id = c.habitId AND c.epochDay = :epochDay
        WHERE h.startDateEpochDay <= :epochDay 
        AND (h.endDateEpochDay IS NULL OR h.endDateEpochDay >= :epochDay)
        """
    )
    fun getHabitsWithCompletionOnDate(epochDay: Long): Flow<List<HabitWithCompletionOnDate>>


    // 4. Get a specific habit and all its completion history
    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitWithCompletionsById(habitId: Long): Flow<HabitWithCompletions>


    // Get all completions for a habit
    @Query("SELECT * FROM completions WHERE habitId = :habitId")
    suspend fun getCompletionsForHabit(habitId: Long): List<CompletionEntity>



    /* ----------  Delete operations  ---------- */

    // 1. Delete a habit and its completions
    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    // 2. reset habit progress
    @Transaction
    suspend fun resetHabit(habitId: Long) {
        deleteCompletionsForHabit(habitId = habitId)
        updateStreaks(habitId = habitId, currentStreak = 0, bestStreak = 0)
    }

    // delete all completions for a habit
    @Query("DELETE FROM completions WHERE habitId = :habitId")
    suspend fun deleteCompletionsForHabit(habitId: Long)

    // Delete all completions before a specific date
    @Query("DELETE FROM completions WHERE habitId = :habitId AND epochDay < :epochDay")
    suspend fun deleteCompletionBeforeDate(habitId: Long, epochDay: Long)

    // Delete all completions after a specific date
    @Query("DELETE FROM completions WHERE habitId = :habitId AND epochDay > :epochDay")
    suspend fun deleteCompletionAfterDate(habitId: Long, epochDay: Long)

}


fun calculateStreaks(
    completions: List<CompletionEntity>
): Pair<Int, Int> {
    val completionDays = completions
        .filter { it.isComplete }
        .map { it.epochDay }
        .sorted()

    if (completionDays.isEmpty()) return 0 to 0

    var bestStreak = 0
    val currentStreak: Int
    var tempStreak = 1

    // to calculate best streak
    for (i in 1 until completionDays.size) {
        val prevDay = completionDays[i - 1]
        val currentDay = completionDays[i]

        if (currentDay - prevDay == 1L) {
            tempStreak++
        } else {
            bestStreak = maxOf(bestStreak, tempStreak)
            tempStreak = 1
        }
    }

    bestStreak = maxOf(bestStreak, tempStreak)

    // Calculate current streak from today going backward
    var streak = 0L
    var streakDay = LocalDate.now().toEpochDay()

    val completedSet = completionDays.toSet()
    while (completedSet.contains(streakDay)) {
        streak++
        streakDay--
    }

    currentStreak = streak.toInt()

    return currentStreak to bestStreak
}