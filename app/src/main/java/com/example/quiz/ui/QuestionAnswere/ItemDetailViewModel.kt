package com.example.quiz.ui.QuestionAnswere

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.data.entities.SearchItem
import com.example.quiz.data.repository.QuestionAnswereRepository
import com.example.quiz.data.repository.QuestionRepository
import kotlinx.coroutines.launch


class ItemDetailViewModel @ViewModelInject constructor(
    private val repository: QuestionRepository
) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    lateinit var searchItem :LiveData<SearchItem>

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun getSearchItem(title: String?) = viewModelScope.launch {
        searchItem = repository.getSearchItem(title)
    }
}

