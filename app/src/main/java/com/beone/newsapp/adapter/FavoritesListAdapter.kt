package com.beone.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beone.newsapp.database.Favorites
import com.beone.newsapp.databinding.FavoritesListItemBinding
import com.beone.newsapp.ui.FavoritesFragmentDirections


class FavoritesListAdapter : ListAdapter<Favorites, FavoritesListAdapter.FavoritesViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            FavoritesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }


    class FavoritesViewHolder(private var binding: FavoritesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.news?.let { news ->
                    navigateToNews(news, it)
                }
            }
        }

        private fun navigateToNews(news: Favorites, it: View) {
            val direction =
                FavoritesFragmentDirections.favoritesFragmentToNewsDetailFragment(news.urlToArticle)
            it.findNavController().navigate(direction)
        }

        fun bind(favorites: Favorites) {
            binding.news = favorites
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem.urlToArticle == newItem.urlToArticle
        }
    }

}