package com.stdev.takequiz.data.model


data class Question(
    var id: String = "0",
    var question: String = "",
    var optionOne: String = "",
    var optionTwo: String = "",
    var optionThree: String = "",
    var optionFour: String = "",
    var correctAnswer: Int = 0,
)