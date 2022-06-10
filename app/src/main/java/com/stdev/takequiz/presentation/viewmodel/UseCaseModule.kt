package com.stdev.takequiz.presentation.viewmodel

import com.stdev.takequiz.domain.repository.QuizRepository
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetQuizUseCase(quizRepository: QuizRepository) : GetQuizUseCase{
        return GetQuizUseCase(quizRepository)
    }

}