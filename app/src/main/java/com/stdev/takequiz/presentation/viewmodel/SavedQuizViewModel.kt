package com.stdev.takequiz.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.domain.usecase.DeleteQuizUseCase
import com.stdev.takequiz.domain.usecase.GetSavedQuizUseCase
import com.stdev.takequiz.domain.usecase.SaveQuizUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedQuizViewModel(private val getSavedQuizUseCase: GetSavedQuizUseCase,private val deleteQuizUseCase: DeleteQuizUseCase,private val saveQuizUseCase: SaveQuizUseCase) : ViewModel() {

    fun getSavedQuiz() = liveData {
        getSavedQuizUseCase.execute().collect{
            emit(it)
        }
    }

    fun deleteQuiz(quiz: Quiz) = viewModelScope.launch {
        deleteQuizUseCase.execute(quiz)
    }

    fun saveQuiz(quiz : Quiz) = viewModelScope.launch {
        saveQuizUseCase.execute(quiz)
    }


}