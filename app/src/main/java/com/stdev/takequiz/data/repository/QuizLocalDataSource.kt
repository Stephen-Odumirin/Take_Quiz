package com.stdev.takequiz.data.repository

import com.stdev.takequiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizLocalDataSource {

    suspend fun saveQuiz(quiz : Quiz)

    fun getQuiz() : Flow<List<Quiz>>

    suspend fun deleteQuiz(quiz: Quiz)

    suspend fun getQuizWithID(id : Long) : Quiz
}