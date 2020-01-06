package com.beone.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beone.newsapp.databinding.NewsListItemBinding
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.ui.HomeViewPageFragmentDirections


class TopNewsListAdapter(private val clickListener: NewsListener) :
    ListAdapter<TopNews, TopNewsListAdapter.TopNewsViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        return TopNewsViewHolder(
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderTop: TopNewsViewHolder, position: Int) {
        val news = getItem(position)
        holderTop.bind(news, clickListener)
    }


    class TopNewsViewHolder(private var binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(topNews: TopNews, clickListener: NewsListener) {
            binding.news = topNews
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<TopNews>() {
        override fun areItemsTheSame(oldItem: TopNews, newItem: TopNews): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TopNews, newItem: TopNews): Boolean {
            return oldItem.urlToArticle == newItem.urlToArticle
        }
    }

}

class NewsListener(val clickListener: (topNews: TopNews) -> Unit) {
    fun onClick(news: TopNews) = clickListener(news)
}