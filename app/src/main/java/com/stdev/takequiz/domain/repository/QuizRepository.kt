package com.stdev.takequiz.domain.repository

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface QuizRepository {

    suspend fun getQuiz(amount : Int,category : Int,difficulty : String,type : String) : List<QuizResult>
    suspend fun saveQuiz(quiz : Quiz)
    fun getQuizFromDb() : Flow<List<Quiz>>
    suspend fun getQuiz2(amount: Int, category: Int, difficulty: String, type: String): Quiz
    suspend fun deleteQuizFromDb(quiz: Quiz)
    suspend fun getQuizWithID(id : Long) : Quiz

}