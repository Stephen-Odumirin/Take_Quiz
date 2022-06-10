package com.stdev.takequiz.domain.repository

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import retrofit2.Response

interface QuizRepository {

    suspend fun getQuiz(amount : Int,category : Int,difficulty : String,type : String) : List<QuizResult>

}