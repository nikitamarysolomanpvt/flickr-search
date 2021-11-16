package com.flickr.android.view.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flickr.android.data.repository.SearchItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.flickr.android.data.model.SearchItem
import com.flickr.android.data.model.SearchResults
import com.flickr.android.utils.ApiException
import com.flickr.android.utils.NoInternetException
import com.flickr.android.utils.State
import kotlinx.coroutines.launch

class SearchItemListViewModel @ViewModelInject constructor(
    private val repository: SearchItemRepository
) : ViewModel() {

    private var itemList = ArrayList<SearchItem?>()

    private val _itemsLiveData = MutableLiveData<State<ArrayList<SearchItem?>>>()
    val itemsLiveData: LiveData<State<ArrayList<SearchItem?>>>
        get() = _itemsLiveData

    private val _itemNameLiveData = MutableLiveData<String>()
    val itemNameLiveData: LiveData<String>
        get() = _itemNameLiveData
    private val _loadMoreListLiveData = MutableLiveData<Boolean>()

    private lateinit var itemResponse: SearchResults

    init {
        _loadMoreListLiveData.value = false
        _itemNameLiveData.value = ""
    }

    fun getSearchItems(query:String) {
        _itemsLiveData.postValue(State.loading())
        _itemNameLiveData.value = query
        if (_itemNameLiveData.value.isNullOrEmpty()) {
            itemList.clear()
            _itemsLiveData.value=null
        } else
        viewModelScope.launch(Dispatchers.IO) {
            if (_itemNameLiveData.value != null && _itemNameLiveData.value!!.isNotEmpty()) {
                try {
                    itemResponse = repository.getSearchItems(
                        _itemNameLiveData.value!!
                    )
                    withContext(Dispatchers.Main) {
                        if (itemResponse!=null) {
                            itemList.clear()
                            itemList.addAll(itemResponse.items)
                            _itemsLiveData.postValue(State.success(itemList))
                            _loadMoreListLiveData.value = false
                        } else
                            _itemsLiveData.postValue(State.error("itemResponse.error"))
                    }
                } catch (e: ApiException) {
                    withContext(Dispatchers.Main) {
                        _itemsLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                } catch (e: NoInternetException) {
                    withContext(Dispatchers.Main) {
                        _itemsLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }

        }
    }
}
