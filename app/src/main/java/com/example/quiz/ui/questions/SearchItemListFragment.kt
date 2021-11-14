package com.example.quiz.ui.questions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.R
import com.example.quiz.data.entities.SearchItem
import com.example.quiz.databinding.SearchItemListFragmentBinding
import com.example.quiz.ui.QuestionAnswere.ItemDetailViewModel
import com.example.quiz.utils.Resource
import com.example.quiz.utils.autoCleared
import com.example.quiz.utils.dismissKeyboard
import dagger.hilt.android.AndroidEntryPoint
import com.github.dhaval2404.imagepicker.ImagePicker

const val PROFILE_IMAGE_REQ_CODE = 101

@AndroidEntryPoint
class SearchItemListFragment : Fragment() {

    private var binding: SearchItemListFragmentBinding by autoCleared()
    private val viewModel: SearchItemListViewModel by viewModels()
    private lateinit var searchView: SearchView
    private lateinit var adapter: SearchItemListAdapter
    private var imgPreviewUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setHasOptionsMenu(true)
//        binding.submitBtn.setOnClickListener {
//            adapter.getItems()?.let {
//                populateQuestionAnswers(it)
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = "Search"
            isSubmitButtonEnabled = true
            onActionViewExpanded()
        }
        search(searchView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return NavigationUI.onNavDestinationSelected(item!!,
//          findNavController())
//                || super.onOptionsItemSelected(item)
//    }
    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.dismissKeyboard(searchView)
                searchView.clearFocus()
                viewModel.query=query
                viewModel.questions
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
    private fun setupRecyclerView() {

        adapter = SearchItemListAdapter(this)
        adapter.listenerClick = {
            onClickedSubmitQuestion(it.title)
        }

        with(binding) {
            questionsRv.layoutManager = LinearLayoutManager(requireContext())
            questionsRv.adapter = adapter
            questionsRv.itemAnimator!!.changeDuration = 0

        }
    }

    private fun setupObservers() {
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty())
                        populateRecyclerView(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun populateRecyclerView(questions: ArrayList<SearchItem>) {
        if (questions.isNotEmpty())
            adapter.setItems(questions)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                // Uri object will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                when (requestCode) {
                    PROFILE_IMAGE_REQ_CODE -> {
                        imgPreviewUri = uri
//                        imgPreview.visibility=View.VISIBLE
//                        imgPreview.setLocalImage(uri)
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this.context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this.context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun onClickedSubmitQuestion(title: String) {
        findNavController().navigate(
            R.id.action_searchItemListFragment_to_itemDetailFragment, bundleOf("title" to title)
        )

    }

}
