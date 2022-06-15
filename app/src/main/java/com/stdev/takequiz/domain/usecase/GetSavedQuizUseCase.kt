package com.stdev.takequiz.domain.usecase

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class GetSavedQuizUseCase(private val repository: QuizRepository) {

    fun execute() : Flow<List<Quiz>> {
        return repository.getQuizFromDb()
    }

}