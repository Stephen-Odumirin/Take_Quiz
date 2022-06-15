package com.stdev.takequiz.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import com.stdev.takequiz.domain.usecase.GetQuizWithIdUseCase
import com.stdev.takequiz.domain.usecase.SaveQuizUseCase

class QuizViewModelFactory(
    private val getQuizUseCase: GetQuizUseCase,
    private val saveQuizUseCase: SaveQuizUseCase,
    private val getQuizWithIdUseCase: GetQuizWithIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(getQuizUseCase, saveQuizUseCase,getQuizWithIdUseCase) as T
    }

}