package com.beone.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.network.topnews.asTopNewsDatabaseModel
import com.beone.newsapp.repository.NewsRepository
import kotlinx.coroutines.*

enum class ApiStatus { ERROR, DONE }
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val database = getDatabase(application)
    private val newsRepository = NewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val networkTopHeadlines = newsRepository.topNews
    fun refreshNews() {
        viewModelScope.launch {
            try {
                val newsList =
                    withContext(Dispatchers.IO) {
                        newsRepository.refreshNews()
                    }
                withContext(Dispatchers.IO) {
                    database.topNewsDao.insertTopNews(
                        newsList.asTopNewsDatabaseModel()
                    )
                }
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.d("RequestError: ", "$e")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}