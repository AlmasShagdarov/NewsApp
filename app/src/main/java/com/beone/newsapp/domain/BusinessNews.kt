package com.beone.newsapp.domain

import com.beone.newsapp.database.Favorites
import com.beone.newsapp.util.eraseSourceInfo

data class BusinessNews constructor(
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

fun BusinessNews.asFavoritesModel(): Favorites {
    return Favorites(
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

