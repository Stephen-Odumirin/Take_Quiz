package com.stdev.takequiz.data.repository

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.domain.repository.QuizRepository
import retrofit2.Response

class QuizRepositoryImpl(private val quizRemoteDataSource: QuizRemoteDataSource) : QuizRepository {
    override suspend fun getQuiz(amount: Int, category: Int, difficulty: String, type: String): List<QuizResult> {
        val quizList = getQuizFromApi(amount, category, difficulty, type)
        return quizList
    }

    suspend fun getQuizFromApi(amount: Int, category: Int, difficulty: String, type: String) : List<QuizResult>{
        val quizList: List<QuizResult>
        val response = quizRemoteDataSource.getQuiz(amount, category, difficulty, type)
        val body = response.body()
        quizList = body?.results ?: listOf()
        return quizList
    }

}