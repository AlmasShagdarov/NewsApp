package com.beone.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beone.newsapp.util.eraseSourceInfo


@Entity
data class Favorites constructor(
    @PrimaryKey
    val urlToArticle: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedTime: String,
    val content: String?,
    val author: String?,
    val sourceInfo: String?
) {
    val formattedTitle: String
        get() = title.eraseSourceInfo()
}