package com.beone.newsapp.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("us")).parse(this) ?: Date()
}

fun String.eraseCharNumber(): String {
    val descriptionRegex = Regex("\\[\\+\\d*\\schars]")
    return replace(descriptionRegex, "")
}

fun String.eraseSourceInfo(): String {
    val titleSourceRegex = Regex(" - (.*)$")
    return replace(titleSourceRegex, "")
}