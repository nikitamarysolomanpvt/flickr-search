package com.flickr.android.view.search

import org.junit.After
import org.junit.Test
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchItemListFragmentTest {
    @Mock
    private lateinit var searchItemListFragment: SearchItemListFragment

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchItemListFragment = mock(SearchItemListFragment::class.java)

    }
    @Test
    fun getSearchViewSuggestionList() {
        var searchViewSuggestions= mutableListOf("You have no recent searches.  Enter a search term above")

        `when`(searchItemListFragment.getSearchViewSuggestionList(searchViewSuggestions,"test")
        ).thenCallRealMethod().thenReturn(searchViewSuggestions)
        Assert.assertEquals(searchViewSuggestions.size, 1)
    }

    @After
    fun tearDown() {
    }
}