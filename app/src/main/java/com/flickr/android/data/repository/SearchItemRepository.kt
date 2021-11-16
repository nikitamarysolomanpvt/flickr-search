package com.flickr.android.data.repository

import com.flickr.android.data.remote.SearchItemDataSource
import javax.inject.Inject

class SearchItemRepository @Inject constructor(
    private val remoteDataSource: SearchItemDataSource
) {
    suspend fun getSearchItems(query: String) = remoteDataSource.getAllSearchItems(query)
}