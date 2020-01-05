package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.NewsDetailRepository
import com.beone.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NewsDetailViewModel(application: Application, newsId: String) :
    AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val newsDetailRepository = NewsDetailRepository(database)

    val topNewsById = newsDetailRepository.getTopNewsById(newsId)
    val isFavorite = newsDetailRepository.isFavorite(newsId)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun removeFromFavorites() {
        viewModelScope.launch {
            newsDetailRepository.delete(topNewsById.value!!)
        }
    }

    fun addToFavorites() {
        viewModelScope.launch {
            newsDetailRepository.insert(topNewsById.value!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}