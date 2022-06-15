package com.stdev.takequiz.domain.usecase

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.domain.repository.QuizRepository

class DeleteQuizUseCase(private val quizRepository: QuizRepository) {

    suspend fun execute(quiz : Quiz){
        quizRepository.deleteQuizFromDb(quiz)
    }

}