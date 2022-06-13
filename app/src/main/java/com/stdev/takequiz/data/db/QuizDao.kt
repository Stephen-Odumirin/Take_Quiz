package com.stdev.takequiz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stdev.takequiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuiz(quiz : Quiz)

    @Query("select * from quiz_list")
    fun getQuiz() : Flow<List<Quiz>>

}