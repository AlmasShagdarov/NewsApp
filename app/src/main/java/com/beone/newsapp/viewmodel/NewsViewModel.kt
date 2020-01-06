package com.beone.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.network.Category
import com.beone.newsapp.network.topnews.asTopNewsDatabaseModel
import com.beone.newsapp.repository.NewsRepository
import kotlinx.coroutines.*


class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val newsRepository = NewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val status = newsRepository.status
    val networkTopHeadlines = newsRepository.topNews
    fun refreshNews() {
        viewModelScope.launch {
            newsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}