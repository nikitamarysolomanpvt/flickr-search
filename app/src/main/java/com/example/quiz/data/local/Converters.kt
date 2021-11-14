package com.example.quiz.data.local

import androidx.room.TypeConverter
import com.example.quiz.data.entities.Media
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromString(media: String): Media {

        return Media(media)
    }

    @JvmStatic
    @TypeConverter
    fun fromArrayList(media: Media): String {
        return  media.m
    }


}