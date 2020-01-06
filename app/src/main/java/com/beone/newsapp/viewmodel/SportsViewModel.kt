package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.SportsNewsRepository
import kotlinx.coroutines.*

class SportsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val sportsNewsRepository = SportsNewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val status = sportsNewsRepository.status
    val sportsNews = sportsNewsRepository.sportsNews
    fun refreshNews() {
        viewModelScope.launch {
            sportsNewsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}