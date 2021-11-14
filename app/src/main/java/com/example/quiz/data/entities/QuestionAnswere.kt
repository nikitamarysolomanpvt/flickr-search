package com.example.quiz.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionAnswere")
data class QuestionAnswere(
    val text : String,
    @PrimaryKey
    val answere : String
)