package com.beone.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopNewsDao {

    @Query("select * from databasetopnews order by publishedTime desc")
    fun getNews(): LiveData<List<DatabaseTopNews>>

    @Query("select * from databasetopnews where urlToArticle = :newsId")
    fun getNewsById(newsId: String): LiveData<DatabaseTopNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopNews(news: List<DatabaseTopNews>)

}