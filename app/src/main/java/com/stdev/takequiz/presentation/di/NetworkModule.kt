package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.BuildConfig
import com.stdev.takequiz.data.api.QuizApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://opentdb.com")
            .build()
    }

    @Singleton
    @Provides
    fun providesQuizApiService(retrofit: Retrofit) : QuizApiService{
        return retrofit.create(QuizApiService::class.java)
    }

}