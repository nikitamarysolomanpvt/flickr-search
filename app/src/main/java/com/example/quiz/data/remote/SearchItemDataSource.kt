package com.example.quiz.data.remote

import com.example.quiz.data.entities.SearchResults
import javax.inject.Inject

class SearchItemDataSource @Inject constructor(
    private val searchItemService: SearchItemService
) : SafeApiRequest() {
    suspend fun getAllSearchItems(searchTitle: String): SearchResults {
        return apiRequest { searchItemService.getSearchItems(searchTitle) }
    }
}