package com.beone.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.Favorites
import com.beone.newsapp.database.NewsDatabase

class FavoritesRepository(private val database: NewsDatabase) {

    val favorites: LiveData<List<Favorites>> =
        Transformations.map(database.favoritesDao.getFavoriteNews()) {
            it.reversed()
        }

    fun getFavoritesById(newsId: String) = database.favoritesDao.getFavoriteNewsById(newsId)
}