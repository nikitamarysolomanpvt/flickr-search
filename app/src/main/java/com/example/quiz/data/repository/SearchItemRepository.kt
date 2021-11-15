package com.example.quiz.data.repository

import com.example.quiz.data.remote.SearchItemDataSource
import javax.inject.Inject

class SearchItemRepository @Inject constructor(
    private val remoteDataSource: SearchItemDataSource
) {
    suspend fun getSearchItems(query: String) = remoteDataSource.getAllSearchItems(query)
}