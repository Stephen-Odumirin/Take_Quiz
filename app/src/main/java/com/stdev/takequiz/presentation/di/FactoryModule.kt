package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.domain.usecase.*
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
import com.stdev.takequiz.presentation.viewmodel.SavedQuizViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesQuizFactoryModel(getQuizUseCase: GetQuizUseCase, saveQuizUseCase: SaveQuizUseCase,getQuizWithIdUseCase: GetQuizWithIdUseCase) : QuizViewModelFactory{
        return QuizViewModelFactory(getQuizUseCase, saveQuizUseCase,getQuizWithIdUseCase)
    }

    @Singleton
    @Provides
    fun providesSavedQuizFactoryModel(getSavedQuizUseCase: GetSavedQuizUseCase,deleteQuizUseCase: DeleteQuizUseCase,saveQuizUseCase: SaveQuizUseCase) : SavedQuizViewModelFactory{
        return SavedQuizViewModelFactory(getSavedQuizUseCase, deleteQuizUseCase,saveQuizUseCase)
    }

}