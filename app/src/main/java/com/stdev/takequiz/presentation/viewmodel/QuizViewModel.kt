package com.stdev.takequiz.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.domain.usecase.GetQuizUseCase
import kotlinx.coroutines.launch

class QuizViewModel(
    private val app : Application,
    private val getQuizUseCase: GetQuizUseCase
) : ViewModel() {

    val quizList : MutableLiveData<List<QuizResult>> = MutableLiveData()

    fun getQuiz(amount : Int, category : Int, difficulty : String,type : String) = viewModelScope.launch {
        val result = getQuizUseCase.execute(amount, category, difficulty, type)
        quizList.postValue(result)
    }

}