package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TechnologyViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TechnologyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TechnologyViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}