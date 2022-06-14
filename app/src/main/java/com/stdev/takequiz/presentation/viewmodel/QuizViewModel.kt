package com.stdev.takequiz.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import com.stdev.takequiz.domain.usecase.SaveQuizUseCase
import kotlinx.coroutines.launch

class QuizViewModel(
    private val app : Application,
    private val getQuizUseCase: GetQuizUseCase,
    private val saveQuizUseCase: SaveQuizUseCase
) : ViewModel() {

    private val _quiz : MutableLiveData<Quiz> = MutableLiveData()
    val quiz : LiveData<Quiz> get() = _quiz

    fun getQuiz(amount : Int, category : Int, difficulty : String, type : String) = viewModelScope.launch {
        val result = getQuizUseCase.execute2(amount, category, difficulty, type)
        _quiz.postValue(result)
    }

    suspend fun saveQuiz(quiz: Quiz) = viewModelScope.launch {
        saveQuizUseCase.execute(quiz)
    }



}