package com.beone.newsapp.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beone.newsapp.databinding.GridViewItemBinding
import com.beone.newsapp.network.News

class NewsGridAdapter : ListAdapter<News, NewsGridAdapter.NewsViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }


    class NewsViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News){
            binding.news = news
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

}