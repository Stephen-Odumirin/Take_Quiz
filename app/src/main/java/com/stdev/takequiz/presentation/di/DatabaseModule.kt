package com.stdev.takequiz.presentation.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.stdev.takequiz.data.db.QuizDao
import com.stdev.takequiz.data.db.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesQuizDatabase(app : Application) : QuizDatabase{
        return Room.databaseBuilder(app,QuizDatabase::class.java,"quiz_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providesQuizDao(database: QuizDatabase) : QuizDao{
        return database.quizDao()
    }


}