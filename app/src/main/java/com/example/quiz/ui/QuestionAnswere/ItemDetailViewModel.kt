package com.example.quiz.ui.QuestionAnswere

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.quiz.data.entities.SearchItem
import com.example.quiz.data.repository.SearchItemRepository
import kotlinx.coroutines.launch


class ItemDetailViewModel @ViewModelInject constructor(
    private val repository: SearchItemRepository
) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    lateinit var searchItem :MutableLiveData<SearchItem>
}

