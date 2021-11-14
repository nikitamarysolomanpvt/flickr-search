package com.example.quiz.ui.questions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.quiz.R
import com.example.quiz.data.entities.SearchItem
import com.example.quiz.databinding.SearchedItemBinding
import kotlin.collections.ArrayList

class SearchItemListAdapter(private val context: SearchItemListFragment) :
    RecyclerView.Adapter<SearchItemListAdapter.SearchItemViewHolder>() {

     lateinit var listenerClick:(SearchItem)->Unit

    private val items = ArrayList<SearchItem>()

    fun setItems(items: ArrayList<SearchItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<SearchItem>? {
        return this.items
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchedItemBinding.inflate(inflater, parent, false)

        return SearchItemViewHolder(binding, context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(items[position])

    }


    inner class SearchItemViewHolder(
        private val itemBinding: SearchedItemBinding,
        private val context: SearchItemListFragment
    ) : RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var item: SearchItem


        @SuppressLint("SetTextI18n")
        fun bind(item: SearchItem) {
            Glide.with(itemBinding.imagePoster.context).load(item.media.m)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imagePoster)
            itemBinding.textTitle.text=item.title
            itemBinding.item.setOnClickListener { listenerClick.invoke(item) }
        }

    }
}




