package com.beone.newsapp.network

import com.squareup.moshi.Json

data class News(
    val source: SourceInfo,
    val author: String,
    val title: String,
    val description: String,
    @Json(name = "url")
    val urlToArticle: String,
    val urlToImage: String,
    @Json(name = "publishedAt")
    val publishedTime: String,
    val content: String?

)