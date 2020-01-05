package com.beone.newsapp.network.topnews

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkTopNews(
    val source: SourceInfo,
    val title: String,
    val description: String?,
    val author: String?,
    @Json(name = "url")
    val urlToArticle: String,
    val urlToImage: String?,
    @Json(name = "publishedAt")
    val publishedTime: String,
    val content: String?
)

