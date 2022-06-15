package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.domain.repository.QuizRepository
import com.stdev.takequiz.domain.usecase.*
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

    @Singleton
    @Provides
    fun providesSaveQuizUseCase(quizRepository: QuizRepository) : SaveQuizUseCase{
        return SaveQuizUseCase(quizRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedQuizUseCase(quizRepository: QuizRepository) : GetSavedQuizUseCase{
        return GetSavedQuizUseCase(quizRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteQuizUseCase(quizRepository: QuizRepository) : DeleteQuizUseCase{
        return DeleteQuizUseCase(quizRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedQuizWithId(quizRepository: QuizRepository) : GetQuizWithIdUseCase{
        return GetQuizWithIdUseCase(quizRepository)
    }

}