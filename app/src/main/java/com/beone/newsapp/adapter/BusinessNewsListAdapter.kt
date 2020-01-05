package com.beone.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beone.newsapp.databinding.BusinessListItemBinding
import com.beone.newsapp.domain.BusinessNews
import com.beone.newsapp.ui.HomeViewPageFragmentDirections

class BusinessNewsListAdapter :
    ListAdapter<BusinessNews, BusinessNewsListAdapter.BusinessNewsViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessNewsViewHolder {
        return BusinessNewsViewHolder(
            BusinessListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BusinessNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }


    class BusinessNewsViewHolder(private var binding: BusinessListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.news?.let { news ->
                    //  navigateToNews(news, it)
                }
            }
        }

        private fun navigateToNews(news: BusinessNews, it: View) {
            val direction =
                HomeViewPageFragmentDirections.actionHomeViewPageFragmentToNewsDetailFragment(news.urlToArticle)
            it.findNavController().navigate(direction)
        }

        fun bind(businessNews: BusinessNews) {
            binding.news = businessNews
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<BusinessNews>() {
        override fun areItemsTheSame(oldItem: BusinessNews, newItem: BusinessNews): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BusinessNews, newItem: BusinessNews): Boolean {
            return oldItem.urlToArticle == newItem.urlToArticle
        }
    }

}