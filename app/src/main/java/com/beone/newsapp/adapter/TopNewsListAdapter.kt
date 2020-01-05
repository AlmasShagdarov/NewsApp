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


class TopNewsListAdapter : ListAdapter<TopNews, TopNewsListAdapter.TopNewsViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        return TopNewsViewHolder(
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderTop: TopNewsViewHolder, position: Int) {
        val news = getItem(position)
        holderTop.bind(news)
    }


    class TopNewsViewHolder(private var binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.news?.let { news ->
                    navigateToNews(news, it)
                }
            }
        }

        private fun navigateToNews(news: TopNews, it: View) {
            val direction =
                HomeViewPageFragmentDirections.actionHomeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
            it.findNavController().navigate(direction)
        }

        fun bind(topNews: TopNews) {
            binding.news = topNews
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