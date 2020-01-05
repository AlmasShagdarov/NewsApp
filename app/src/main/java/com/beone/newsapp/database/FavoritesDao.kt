package com.beone.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {

    @Query("select * from favorites")
    fun getFavoriteNews(): LiveData<List<Favorites>>

    @Query("select * from favorites where urlToArticle = :newsId")
    fun getFavoriteNewsById(newsId: String): LiveData<Favorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteNews(news: Favorites)

    @Delete
    fun deleteFavoriteNews(news: Favorites)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE urlToArticle = :newsId LIMIT 1)")
    fun isFavorite(newsId: String): LiveData<Boolean>
}