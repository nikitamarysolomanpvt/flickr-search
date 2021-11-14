package com.example.quiz.data.entities


data class SearchResults (

	 val title : String,
	 val link : String,
	 val description : String,
	 val modified : String,
	 val generator : String,
	 val items : List<SearchItem>
)