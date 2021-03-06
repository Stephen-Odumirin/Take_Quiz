package com.stdev.takequiz.data.repository

import com.stdev.takequiz.data.db.QuizDao
import com.stdev.takequiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

class QuizLocalDataSourceImpl(private val quizDao: QuizDao) : QuizLocalDataSource {
    override suspend fun saveQuiz(quiz: Quiz) {
        quizDao.saveQuiz(quiz)
    }

    override fun getQuiz(): Flow<List<Quiz>> {
        return quizDao.getQuiz()
    }

    override suspend fun deleteQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }

    override suspend fun getQuizWithID(id: Long) : Quiz{
        return quizDao.getQuizWithID(id)
    }
}