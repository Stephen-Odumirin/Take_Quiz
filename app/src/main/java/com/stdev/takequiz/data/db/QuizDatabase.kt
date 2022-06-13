package com.stdev.takequiz.data.db

import androidx.room.*
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import retrofit2.http.GET

@Database(entities = [Quiz::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizDao() : QuizDao

}