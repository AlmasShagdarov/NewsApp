package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SportsViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SportsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SportsViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}