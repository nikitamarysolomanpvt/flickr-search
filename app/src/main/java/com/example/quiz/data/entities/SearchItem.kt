package com.example.quiz.data.entities


import com.google.gson.annotations.SerializedName

data class SearchItem (

    @SerializedName("title") val title : String,
    @SerializedName("link") val link : String,
    @SerializedName("media") val media : Media,
    @SerializedName("date_taken") val date_taken : String,
    @SerializedName("description") val description : String,
    @SerializedName("published") val published : String,
    @SerializedName("author") val author : String,
    @SerializedName("author_id") val author_id : String,
    @SerializedName("tags") val tags : String
)