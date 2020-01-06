package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.HealthNewsRepository
import kotlinx.coroutines.*

class HealthViewModel(application: Application) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val healthNewsRepository = HealthNewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val status = healthNewsRepository.status
    val healthNews = healthNewsRepository.healthNews
    fun refreshNews() {
        viewModelScope.launch {
            healthNewsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}