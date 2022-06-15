package com.stdev.takequiz.presentation.di

import com.stdev.takequiz.presentation.adapter.HomeAdapter
import com.stdev.takequiz.presentation.adapter.SavedAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesSavedAdapter() : SavedAdapter{
        return SavedAdapter()
    }

    @Singleton
    @Provides
    fun providesHomeAdapter() : HomeAdapter{
        return HomeAdapter()
    }

}