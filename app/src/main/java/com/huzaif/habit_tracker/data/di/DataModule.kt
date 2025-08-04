package com.huzaif.habit_tracker.data.di

import android.content.Context
import androidx.room.Room
import com.huzaif.habit_tracker.data.local.HabitDAO
import com.huzaif.habit_tracker.data.local.HabitDatabase
import com.huzaif.habit_tracker.data.repository.HabitRepositoryImpl
import com.huzaif.habit_tracker.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room
            .databaseBuilder(context, HabitDatabase::class.java, "habit_db")
            .build()
    }

    @Provides
    fun provideHabitDao(db: HabitDatabase): HabitDAO = db.habitDao()

    @Provides
    fun provideHabitRepository(habitDao: HabitDAO): HabitRepository = HabitRepositoryImpl(habitDao)
}
