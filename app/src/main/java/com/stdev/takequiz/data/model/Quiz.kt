package com.stdev.takequiz.data.model


import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val results: List<QuizResult>
)