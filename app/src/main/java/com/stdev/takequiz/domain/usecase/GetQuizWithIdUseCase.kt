package com.stdev.takequiz.domain.usecase

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.domain.repository.QuizRepository

class GetQuizWithIdUseCase(private val repository: QuizRepository) {

    suspend fun execute(id : Long) : Quiz{
        return repository.getQuizWithID(id)
    }

}