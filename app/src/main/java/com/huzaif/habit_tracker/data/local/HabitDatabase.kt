package com.huzaif.habit_tracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huzaif.habit_tracker.data.model.CompletionEntity
import com.huzaif.habit_tracker.data.model.HabitEntity

@Database(
    entities = [HabitEntity::class, CompletionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDAO

}
