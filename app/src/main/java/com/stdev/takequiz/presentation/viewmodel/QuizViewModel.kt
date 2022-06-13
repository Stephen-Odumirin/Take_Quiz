package com.stdev.takequiz.presentation.viewmodel

import android.app.Application
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

    val quizList : MutableLiveData<List<QuizResult>> = MutableLiveData()
    val quiz : MutableLiveData<Quiz> = MutableLiveData()

    fun getQuiz(amount : Int, category : Int, difficulty : String,type : String) = viewModelScope.launch {
        val result = getQuizUseCase.execute(amount, category, difficulty, type)
        quizList.postValue(result)
    }

    fun getQuiz2(amount : Int, category : Int, difficulty : String,type : String) = viewModelScope.launch {
        val result = getQuizUseCase.execute2(amount, category, difficulty, type)
        quiz.postValue(result)
    }

    //fun saveArticle(article: Article) = viewModelScope.launch {
    //        saveNewsUseCase.execute(article)
    //    }

    suspend fun saveQuiz(quiz: Quiz) = viewModelScope.launch {
        saveQuizUseCase.execute(quiz)
    }



}