package com.stdev.takequiz.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stdev.takequiz.data.model.Quiz
import com.stdev.takequiz.data.model.QuizResult
import java.lang.reflect.Type
import java.util.*

class Converters {
    @TypeConverter
    fun stringToListServer(data: String?): List<QuizResult?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<QuizResult?>?>() {}.type
        return Gson().fromJson<List<QuizResult?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<QuizResult?>?): String? {
        return Gson().toJson(someObjects)
    }


    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object :
            TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }


}