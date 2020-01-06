package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EntertainmentViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntertainmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntertainmentViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}