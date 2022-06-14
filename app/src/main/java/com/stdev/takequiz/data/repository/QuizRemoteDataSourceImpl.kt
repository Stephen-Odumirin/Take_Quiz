package com.stdev.takequiz.data.repository

import com.stdev.takequiz.data.api.QuizApiService
import com.stdev.takequiz.data.model.Quiz
import retrofit2.Response

class QuizRemoteDataSourceImpl(private val apiService: QuizApiService) : QuizRemoteDataSource {
    override suspend fun getQuiz(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Response<Quiz> {

        return apiService.getQuiz(amount, category, difficulty, type)
    }

}