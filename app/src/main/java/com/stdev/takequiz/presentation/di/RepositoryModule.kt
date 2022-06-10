package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.data.repository.QuizRemoteDataSource
import com.stdev.takequiz.data.repository.QuizRepositoryImpl
import com.stdev.takequiz.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesQuizRepository(quizRemoteDataSource: QuizRemoteDataSource) : QuizRepository{
        return QuizRepositoryImpl(quizRemoteDataSource = quizRemoteDataSource)
    }

}