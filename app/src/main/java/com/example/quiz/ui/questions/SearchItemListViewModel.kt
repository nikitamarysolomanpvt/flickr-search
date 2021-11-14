package com.example.quiz.ui.questions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.quiz.data.repository.QuestionRepository

class SearchItemListViewModel @ViewModelInject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    var query:String=""
    val questions = repository.getQuestions(query)

}
