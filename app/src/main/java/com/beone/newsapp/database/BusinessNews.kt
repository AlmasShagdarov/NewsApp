package com.beone.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beone.newsapp.domain.BusinessNews

@Entity
data class DatabaseBusinessNews constructor(
    @PrimaryKey
    val urlToArticle: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedTime: String,
    val content: String?,
    val author: String?,
    val sourceInfo: String?
)

fun List<DatabaseBusinessNews>.asDomainModel(): List<BusinessNews> {
    return map {
        BusinessNews(
            urlToArticle = it.urlToArticle,
            title = it.title,
            description = it.description,
            urlToImage = it.urlToImage,
            publishedTime = it.publishedTime,
            content = it.content,
            author = it.author,
            sourceInfo = it.sourceInfo
        )
    }
}

fun DatabaseBusinessNews.asDomainModel(): BusinessNews {
    return BusinessNews(
        urlToArticle,
        title,
        description,
        urlToImage,
        publishedTime,
        content,
        author,
        sourceInfo
    )
}