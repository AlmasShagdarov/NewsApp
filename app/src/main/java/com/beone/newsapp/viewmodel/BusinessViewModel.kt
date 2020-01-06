package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.BusinessNewsRepository
import kotlinx.coroutines.*

class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val businessNewsRepository = BusinessNewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val status = businessNewsRepository.status
    val businessNews = businessNewsRepository.businessNews
    fun refreshNews() {
        viewModelScope.launch {
            businessNewsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
