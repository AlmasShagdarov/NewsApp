package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.TechnologyNewsRepository
import kotlinx.coroutines.*

class TechnologyViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val techNewsRepository = TechnologyNewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val status = techNewsRepository.status

    val techNews = techNewsRepository.technologyNews
    fun refreshNews() {
        viewModelScope.launch {
            techNewsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}