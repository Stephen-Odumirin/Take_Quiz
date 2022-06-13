package com.stdev.takequiz.domain.usecase

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.domain.repository.QuizRepository

class SaveQuizUseCase(private val quizRepository: QuizRepository) {

    suspend fun execute(quiz : Quiz){
        quizRepository.saveQuiz(quiz)
    }

}