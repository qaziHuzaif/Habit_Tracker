package com.huzaif.habit_tracker.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity

data class HabitWithCompletions(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId",
        entity = CompletionEntity::class
    )
    val completions: List<CompletionEntity>
)

data class HabitWithCompletionOnDate(
    @Embedded val habit: HabitEntity,
    val isComplete: Boolean?          // true = complete, false = explicitly incomplete, null = not marked
)