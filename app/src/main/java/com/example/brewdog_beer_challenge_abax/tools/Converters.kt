package com.example.brewdog_beer_challenge_abax.tools

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// Converters class is being used to store data that originally came in the form of another object,
// in this case a lis of Strings and saved as a Gson object.
// It is not the best for the performance but it saves coding time for this practice

class Converters {

    private val listType: Type = object : TypeToken<List<String?>?>() {}.getType()
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return Gson().fromJson(value, listType)
    }

    private val gson = Gson()
    @TypeConverter
    fun fromList(list: List<String?>?): String {
        return gson.toJson(list)
    }
}