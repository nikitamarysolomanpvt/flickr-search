package com.example.quiz.data.repository

import com.example.quiz.data.local.SearchItemsDao
import com.example.quiz.data.remote.QuestionRemoteDataSource
import com.example.quiz.utils.performGetOperation
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val remoteDataSource: QuestionRemoteDataSource,
    private val localDataSource: SearchItemsDao
) {

    fun getSearchItem(title: String?) = localDataSource.getSearchItem(title)



    fun getQuestions(query: String) = performGetOperation(
        databaseQuery = {
            localDataSource.getAllItems() },
        networkCall = { remoteDataSource.getAllSearchItems(query) },
        saveCallResult = {
            localDataSource.insertAll(it.items) }
    )
}