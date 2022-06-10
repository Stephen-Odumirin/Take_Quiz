package com.stdev.takequiz.presentation.di

import android.app.Application
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import com.stdev.takequiz.presentation.viewmodel.QuizViewModelFactory
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
    fun providesQuizFactoryModel(app : Application,getQuizUseCase: GetQuizUseCase) : QuizViewModelFactory{
        return QuizViewModelFactory(app, getQuizUseCase)
    }

}