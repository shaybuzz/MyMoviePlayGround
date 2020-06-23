package com.tut.mymovieplayground.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConvertersHelper {

    @TypeConverter
    fun fromIds(ids: List<Int>): String {
        return Gson().toJson(ids)
    }

    @TypeConverter
    fun toIds(ids: String): List<Int> {
        val listType: Type = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(ids, listType)
    }

}