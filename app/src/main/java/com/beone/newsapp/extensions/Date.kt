package com.beone.newsapp.extensions

import java.text.SimpleDateFormat
import java.util.*

enum class TimeUnits(val value: Long) {
    SECOND(1000L),
    MINUTE(60 * SECOND.value),
    HOUR(60 * MINUTE.value),
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val dif = kotlin.math.abs(this.time - date.time)

    return when {
        dif <= TimeUnits.SECOND.value -> " - rigth now"
        dif <= TimeUnits.SECOND.value * 45 -> " - few seconds ago"
        dif <= TimeUnits.SECOND.value * 75 -> " - minute ago"
        dif <= TimeUnits.MINUTE.value * 45 -> " - ${(dif / TimeUnits.MINUTE.value).toInt()} minutes ago"
        dif <= TimeUnits.MINUTE.value * 75 -> " - hour ago"
        dif <= TimeUnits.HOUR.value * 22 -> " - ${(dif / TimeUnits.HOUR.value).toInt()} hours ago"
        dif <= TimeUnits.HOUR.value * 26 -> " - yesterday"
        else -> " - ${this.format()}"
    }
}

fun Date.format(pattern: String = "dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.US)
    return dateFormat.format(this)
}


