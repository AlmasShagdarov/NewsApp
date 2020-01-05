package com.beone.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BusinessNewsDao {

    @Query("select * from databasebusinessnews order by publishedTime desc")
    fun getBusinessNews(): LiveData<List<DatabaseBusinessNews>>

    @Query("select * from databasebusinessnews where urlToArticle = :newsId")
    fun getBusinessNewsById(newsId: String): LiveData<DatabaseBusinessNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBusinessNews(news: List<DatabaseBusinessNews>)
}