package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.data.db.QuizDao
import com.stdev.takequiz.data.repository.QuizLocalDataSource
import com.stdev.takequiz.data.repository.QuizLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun providesLocalSource(quizDao: QuizDao) : QuizLocalDataSource{
        return QuizLocalDataSourceImpl(quizDao)
    }

}