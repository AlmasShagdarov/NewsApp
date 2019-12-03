package com.beone.newsapp.network

data class NewsObject(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)