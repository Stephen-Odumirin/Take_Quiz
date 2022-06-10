package com.stdev.takequiz.data.repository

import com.stdev.takequiz.data.model.Quiz
import retrofit2.Response

interface QuizRemoteDataSource {

    suspend fun getQuiz(amount : Int,category : Int,difficulty : String,type : String) : Response<Quiz>

}