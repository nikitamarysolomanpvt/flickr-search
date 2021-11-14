package com.example.quiz.ui.QuestionAnswere

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.quiz.R
import com.example.quiz.data.entities.SearchItem
import com.example.quiz.databinding.ItemDetailFragmentBinding
import com.example.quiz.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private var binding: ItemDetailFragmentBinding by autoCleared()
    private val viewModel: ItemDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        viewModel.getSearchItem(title)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.searchItem.observe(viewLifecycleOwner, Observer {
            bindQuestionAnswers(it!!)
        })
    }

    private fun bindQuestionAnswers(searchItem: SearchItem) {
        Glide.with(binding.imagePoster.context).load(searchItem.media.m)
            .centerCrop()
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imagePoster)

        binding.toolbar.title=searchItem.title
        binding.authorTv.text=searchItem.author

        binding.descriptionTv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(searchItem.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(searchItem.description)
        }
    }
}
