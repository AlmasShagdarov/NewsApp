package com.beone.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beone.newsapp.domain.TopNews

@Entity
data class DatabaseTopNews constructor(
    @PrimaryKey
    val urlToArticle: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedTime: String,
    val content: String?,
    val author: String?,
    val sourceInfo: String?,
    val category: String
)

fun List<DatabaseTopNews>.asDomainModel(): List<TopNews> {
    return map {
        TopNews(
            urlToArticle = it.urlToArticle,
            title = it.title,
            description = it.description,
            urlToImage = it.urlToImage,
            publishedTime = it.publishedTime,
            content = it.content,
            author = it.author,
            sourceInfo = it.sourceInfo,
            category = it.category
        )
    }
}

fun DatabaseTopNews.asDomainModel(): TopNews {
    return TopNews(
        urlToArticle,
        title,
        description,
        urlToImage,
        publishedTime,
        content,
        author,
        sourceInfo,
        category
    )
}
