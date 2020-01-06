package com.beone.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.ScienceNewsRepository
import kotlinx.coroutines.*

class ScienceViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val scienceNewsRepository = ScienceNewsRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val status = scienceNewsRepository.status
    val scienceNews = scienceNewsRepository.scienceNews
    fun refreshNews() {
        viewModelScope.launch {
            scienceNewsRepository.refreshNews()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}