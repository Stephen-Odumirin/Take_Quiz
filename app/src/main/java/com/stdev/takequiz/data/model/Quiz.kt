package com.stdev.takequiz.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz_list")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val results: List<QuizResult>
)