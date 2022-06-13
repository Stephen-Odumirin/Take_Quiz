package com.stdev.takequiz.domain.usecase

import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import com.stdev.takequiz.domain.repository.QuizRepository
import retrofit2.Response

class GetQuizUseCase(private val quizRepository: QuizRepository) {

    suspend fun execute(amount : Int,category : Int,difficulty : String,type : String) : List<QuizResult>{
        return quizRepository.getQuiz(amount, category, difficulty, type)
    }

    suspend fun execute2(amount : Int,category : Int,difficulty : String,type : String) : Quiz?{
        return quizRepository.getQuiz2(amount, category, difficulty, type)
    }

}