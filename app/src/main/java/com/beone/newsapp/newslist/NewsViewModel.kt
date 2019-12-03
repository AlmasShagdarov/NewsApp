package com.beone.newsapp.newslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beone.newsapp.network.News
import com.beone.newsapp.network.NewsApi
import kotlinx.coroutines.*

enum class MarsApiStatus { LOADING, ERROR, DONE }
class NewsViewModel : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _topHeadlines = MutableLiveData<List<News>>()
    val topHeadlines: LiveData<List<News>>
        get() = _topHeadlines

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getNewsTopHeadlines()
    }

    private fun getNewsTopHeadlines() {
        coroutineScope.launch {
            lateinit var listResult: List<News>
            withContext(Dispatchers.IO){
                listResult = NewsApi.retrofitService.getTopHeadlinesAsync("us", "f496507f86674de79a0b5e7a875f6d4e").articles
            }
            _topHeadlines.value = listResult
            Log.d("Status:", "Done")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}