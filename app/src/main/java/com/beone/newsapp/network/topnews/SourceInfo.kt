package com.beone.newsapp.network.topnews

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceInfo(
    val id: String?,
    val name: String?
)