package com.beone.newsapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.NewsDatabase
import com.beone.newsapp.database.asDomainModel
import com.beone.newsapp.domain.BusinessNews
import com.beone.newsapp.domain.asFavoritesModel
import com.beone.newsapp.network.NewsApi
import com.beone.newsapp.network.topnews.asBusinessDatabaseModel
import kotlinx.coroutines.*
import retrofit2.HttpException

class BusinessNewsRepository(private val database: NewsDatabase) {

    val businessNews: LiveData<List<BusinessNews>> =
        Transformations.map(database.businessNewsDao.getBusinessNews()) {
            it.asDomainModel()
        }

    private val service = NewsApi.retrofitService
    suspend fun refreshBusinessNews() = withContext(Dispatchers.IO) {
        try {
            val newsList = service.getBusinessNews("us", "business")
            database.businessNewsDao.insertBusinessNews(
                newsList.asBusinessDatabaseModel()
            )
        } catch (e: Exception) {
            Log.d("RequestError: ", "$e")
        }
    }

    fun getBusinessNewsById(newsId: String) =
        Transformations.map(database.businessNewsDao.getBusinessNewsById(newsId)) {
            it.asDomainModel()
        }
}
