package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScienceViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScienceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScienceViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}