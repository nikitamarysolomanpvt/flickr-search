package com.example.quiz.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchitem")
data class SearchItem (

    @PrimaryKey
    val title : String,
    val link : String,
    val media : Media,
    val date_taken : String,
    val description : String,
    val published : String,
    val author : String,
    val author_id : String,
    val tags : String
)