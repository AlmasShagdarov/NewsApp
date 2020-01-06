package com.beone.newsapp.network.topnews

import com.beone.newsapp.database.DatabaseTopNews
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkTopNewsObject(
    val status: String,
    val totalResults: Int,
    val articles: List<NetworkTopNews>
)

fun NetworkTopNewsObject.asTopNewsDatabaseModel(category: String): List<DatabaseTopNews> {
    return articles.map {
        DatabaseTopNews(
            sourceInfo = it.source.name,
            urlToArticle = it.urlToArticle,
            title = it.title,
            description = it.description,
            urlToImage = it.urlToImage,
            publishedTime = it.publishedTime,
            content = it.content,
            author = it.author,
            category = category
        )
    }.filter {
        with(it) {
            content != null &&
                    description != null &&
                    urlToImage != null &&
                    author != null &&
                    sourceInfo != null
        }
    }
}


