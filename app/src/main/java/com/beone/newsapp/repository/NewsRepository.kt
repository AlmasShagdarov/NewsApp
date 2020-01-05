package com.beone.newsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.NewsDatabase
import com.beone.newsapp.database.asDomainModel
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.network.NewsApi
import com.beone.newsapp.network.topnews.asTopNewsDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsRepository(private val database: NewsDatabase) {


    val topNews: LiveData<List<TopNews>> =
        Transformations.map(database.topNewsDao.getNews()) {
            it.asDomainModel()
        }


    private val service = NewsApi.retrofitService
    suspend fun refreshNews() = service.getTopNews("us")


}
