package com.stdev.takequiz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stdev.takequiz.domain.usecase.DeleteQuizUseCase
import com.stdev.takequiz.domain.usecase.GetSavedQuizUseCase
import com.stdev.takequiz.domain.usecase.SaveQuizUseCase

class SavedQuizViewModelFactory(private val getSavedQuizUseCase: GetSavedQuizUseCase,private val deleteQuizUseCase: DeleteQuizUseCase,private val saveQuizUseCase: SaveQuizUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedQuizViewModel(getSavedQuizUseCase, deleteQuizUseCase,saveQuizUseCase) as T
    }

}