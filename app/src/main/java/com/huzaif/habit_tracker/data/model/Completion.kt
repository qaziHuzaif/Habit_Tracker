package com.huzaif.habit_tracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "completions",
    primaryKeys = ["habitId","epochDay"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitId")]
)
data class CompletionEntity(
    val habitId: Long,
    val epochDay: Long,       // LocalDate.toEpochDay()
    val isComplete: Boolean   // false = explicitly incomplete, true = complete
)
