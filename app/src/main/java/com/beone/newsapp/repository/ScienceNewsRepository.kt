package com.beone.newsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.NewsDatabase
import com.beone.newsapp.database.asDomainModel
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.network.ApiStatus
import com.beone.newsapp.network.Category
import com.beone.newsapp.network.NewsApi
import com.beone.newsapp.network.topnews.asTopNewsDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScienceNewsRepository(val database: NewsDatabase) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    val scienceNews: LiveData<List<TopNews>> =
        Transformations.map(database.topNewsDao.getNews(Category.Science.title)) {
            it.asDomainModel()
        }

    private val service = NewsApi.retrofitService
    suspend fun refreshNews() {
        try {
            withContext(Dispatchers.IO) {
                val newsList = service.getTopNews("us", Category.Science.title)
                database.topNewsDao.insertTopNews(newsList.asTopNewsDatabaseModel(Category.Science.title))
            }
            _status.postValue(ApiStatus.DONE)

        } catch (e: Exception) {
            _status.postValue(ApiStatus.ERROR)
        }
    }

}