package com.flickr.android.view.search

import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.flickr.android.R
import com.flickr.android.data.model.SearchItem
import com.flickr.android.databinding.SearchItemListFragmentBinding
import com.flickr.android.view.itemdetail.ItemDetailViewModel
import com.flickr.android.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchItemListFragment : Fragment() {

    private var binding: SearchItemListFragmentBinding by autoCleared()
    private val viewModel: SearchItemListViewModel by activityViewModels()
    private val itemDetailViewModel: ItemDetailViewModel by activityViewModels()
    private lateinit var searchView: SearchView
    private val title = "title"
    private lateinit var adapter: SearchItemListAdapter
    lateinit var searchViewSuggestions: MutableList<String>
    lateinit var tinyDB: TinyDB
    private var mAdapter: SimpleCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val from = arrayOf(title)
        val to = intArrayOf(android.R.id.text1)
        mAdapter = SimpleCursorAdapter(
            activity,
            R.layout.suggetion_list_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
        setupObservers()
        setHasOptionsMenu(true)


    }

    fun init() {
        searchViewSuggestions = mutableListOf(getString(R.string.no_recent_search))
        tinyDB = TinyDB(context)
        tinyDB.putListString("SUGGESTIONS", searchViewSuggestions as ArrayList<String>)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        setSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = SearchItemListAdapter(this)
        adapter.listenerClick = {
            navigateToDetailsFragment(it)
        }

        with(binding) {
            searchItemRv.layoutManager = LinearLayoutManager(requireContext())
            searchItemRv.adapter = adapter
            searchItemRv.itemAnimator!!.changeDuration = 0

        }
    }

    private fun setupObservers() {

        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> {
                    binding.searchItemRv.hide()
                    binding.linearLayoutSearch.hide()
                    binding.progressBar.show()
                }
                is State.Success -> {
                    binding.searchItemRv.show()
                    binding.linearLayoutSearch.hide()
                    binding.progressBar.hide()
                    populateRecyclerView(state.data)
                }
                is State.Error -> {
                    binding.progressBar.hide()
                }
            }
        })
    }

    private fun populateRecyclerView(searchItems: ArrayList<SearchItem?>) {
        adapter.setItems(searchItems)
    }

    fun navigateToDetailsFragment(title: SearchItem) {
        itemDetailViewModel.searchItem = MutableLiveData(title)
        findNavController().navigate(
            R.id.action_searchItemListFragment_to_itemDetailFragment
        )
    }


    private fun setSearchView(menu: Menu) {
        searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.apply {
            suggestionsAdapter = mAdapter
            queryHint = "Search"
            isSubmitButtonEnabled = true
            onActionViewExpanded()
            setQuery(viewModel.itemNameLiveData.value, false)
            // Getting selected (clicked) item suggestion
            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor: Cursor = mAdapter?.getItem(position) as Cursor
                    val txt: String = cursor.getString(cursor.getColumnIndex(title))
                    if (txt != getString(R.string.no_recent_search))
                        searchView.setQuery(txt, true)
                    return true
                }

                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }
            })

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {

                    viewModel.getSearchItems(query)
                    context?.dismissKeyboard(searchView)
                    searchView.clearFocus()
                    searchView.setQuery("", true)
                    searchViewSuggestions =
                        getSearchViewSuggestionList(searchViewSuggestions, query)
                    tinyDB.putListString("SUGGESTIONS", searchViewSuggestions as ArrayList<String>)

                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.getSearchItems(query)
                    populateAdapter()
                    return false
                }
            })
        }
    }

    fun getSearchViewSuggestionList(
        searchViewSuggestions: MutableList<String>,
        query: String
    ): MutableList<String> {
        var searchViewSuggestionList = searchViewSuggestions
        lifecycleScope.launch(Dispatchers.Main) {

            if (searchViewSuggestionList.size >= 6) {
                searchViewSuggestionList.removeAt(0)
            }
            if (!searchViewSuggestionList.contains(query)) {
                searchViewSuggestionList.add(query)
            }
            try {
                if (searchViewSuggestionList.contains(getString(R.string.no_recent_search))) {
                    searchViewSuggestionList.remove(getString(R.string.no_recent_search))
                }
            } catch (e: Exception) {
            }
        }
        return searchViewSuggestionList
    }

    //  implements logic to get data
    private fun populateAdapter() {

        lifecycleScope.launch(Dispatchers.Main) {
            val c = MatrixCursor(arrayOf(BaseColumns._ID, title))

            val asyncValue = async(Dispatchers.IO) {
                searchViewSuggestions = tinyDB.getListString("SUGGESTIONS")


                for (i in searchViewSuggestions.indices) {
                    c.addRow(arrayOf(i, searchViewSuggestions[i]))
                }


            }
            val value = asyncValue.await()
            mAdapter?.changeCursor(c)
        }
    }
}
