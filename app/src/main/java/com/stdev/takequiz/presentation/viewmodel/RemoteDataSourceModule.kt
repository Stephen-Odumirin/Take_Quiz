package com.stdev.takequiz.presentation.viewmodel

import com.stdev.takequiz.data.api.QuizApiService
import com.stdev.takequiz.data.repository.QuizRemoteDataSource
import com.stdev.takequiz.data.repository.QuizRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun providesQuizRemoteDataSourceModule(apiService: QuizApiService) : QuizRemoteDataSource{
        return QuizRemoteDataSourceImpl(apiService)
    }

}
