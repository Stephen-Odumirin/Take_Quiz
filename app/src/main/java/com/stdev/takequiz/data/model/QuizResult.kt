package com.stdev.takequiz.data.model


import com.google.gson.annotations.SerializedName

data class QuizResult(
    @SerializedName("category")
    val category: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>,
    @SerializedName("question")
    val question: String,
    @SerializedName("type")
    val type: String
)