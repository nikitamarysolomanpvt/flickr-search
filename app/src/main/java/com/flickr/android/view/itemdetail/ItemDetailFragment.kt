package com.flickr.android.view.itemdetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.flickr.android.R
import com.flickr.android.data.model.SearchItem
import com.flickr.android.databinding.ItemDetailFragmentBinding
import com.flickr.android.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private var binding: ItemDetailFragmentBinding by autoCleared()
    private val viewModel: ItemDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSearchItem(viewModel.searchItem.value!!)
        setBackNavigation(view)
    }

    private fun setBackNavigation(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationContentDescription(getString(R.string.navigation_back_button))
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun bindSearchItem(searchItem: SearchItem) {

        Glide.with(binding.imagePoster.context).load(searchItem.media.m)
            .centerCrop()
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imagePoster)
        binding.toolbar.title = searchItem.title
        binding.authorTv.text = searchItem.author
        binding.imagePoster.contentDescription=searchItem.title
        binding.descriptionTv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(searchItem.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(searchItem.description)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}
