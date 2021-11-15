package com.example.quiz.data.remote

import com.example.quiz.data.entities.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchItemService {
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getSearchItems(@Query(value = "tags") searchTitle: String): Response<SearchResults>


}