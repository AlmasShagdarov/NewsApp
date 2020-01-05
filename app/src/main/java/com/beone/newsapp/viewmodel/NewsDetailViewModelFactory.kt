package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailViewModelFactory(
    private val app: Application,
    private val newsId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsDetailViewModel(app, newsId) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}