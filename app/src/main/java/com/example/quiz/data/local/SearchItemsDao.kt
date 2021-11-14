package com.example.quiz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiz.data.entities.SearchItem

@Dao
interface SearchItemsDao {

    @Query("SELECT * FROM searchitem")
    fun getAllItems() : LiveData<List<SearchItem>>

    @Query("SELECT * FROM searchitem WHERE title=:title ")
    fun getSearchItem(title: String?) : LiveData<SearchItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<SearchItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: SearchItem)


}