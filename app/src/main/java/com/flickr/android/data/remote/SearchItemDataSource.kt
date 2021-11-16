package com.flickr.android.data.remote

import com.flickr.android.data.model.SearchResults
import javax.inject.Inject

class SearchItemDataSource @Inject constructor(
    private val searchItemService: SearchItemService
) : SafeApiRequest() {
    suspend fun getAllSearchItems(searchTitle: String): SearchResults {
        return apiRequest { searchItemService.getSearchItems(searchTitle) }
    }
}