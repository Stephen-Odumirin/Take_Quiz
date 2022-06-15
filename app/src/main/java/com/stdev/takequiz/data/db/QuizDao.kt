package com.stdev.takequiz.data.db

import androidx.room.*
import com.stdev.takequiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuiz(quiz : Quiz)

    @Query("select * from quiz_list order by id desc")
    fun getQuiz() : Flow<List<Quiz>>

    @Query("select * from quiz_list where id = :id")
    suspend fun getQuizWithID(id : Long) : Quiz

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)


}