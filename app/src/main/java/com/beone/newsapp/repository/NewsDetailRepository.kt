package com.beone.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.NewsDatabase
import com.beone.newsapp.database.asDomainModel
import com.beone.newsapp.domain.TopNews
import com.beone.newsapp.domain.asFavoritesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsDetailRepository(private val database: NewsDatabase) {


    fun isFavorite(newsId: String): LiveData<Boolean> = database.favoritesDao.isFavorite(newsId)
    suspend fun insert(topNews: TopNews) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.insertFavoriteNews(topNews.asFavoritesModel())
        }
    }

    suspend fun delete(topNews: TopNews) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.deleteFavoriteNews(topNews.asFavoritesModel())
        }
    }

    fun getTopNewsById(newsId: String) =
        Transformations.map(database.topNewsDao.getNewsById(newsId)) {
            it.asDomainModel()
        }
}