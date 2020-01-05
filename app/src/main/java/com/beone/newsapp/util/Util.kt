package com.beone.newsapp.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


fun String.eraseSourceInfo(): String {
    val titleSourceRegex = Regex(" - (.*)$")
    return replace(titleSourceRegex, "")
}

fun String.eraseCharNumber(): String {
    val descriptionRegex = Regex("\\[\\+\\d*\\schars]")
    return replace(descriptionRegex, "")
}

fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {

        try {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                Log.i("update_status", "Network is available : true")
                return true
            }
        } catch (e: Exception) {
            Log.i("update_status", "" + e.message)
        }
    }
    Log.i("update_status", "Network is available : FALSE ")
    return false
}

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("us")).parse(this) ?: Date()
}


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





