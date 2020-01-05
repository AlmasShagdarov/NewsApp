package com.beone.newsapp.viewmodel

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beone.newsapp.database.Favorites
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.FavoritesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val favoritesRepository = FavoritesRepository(database)

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val favoriteNews = favoritesRepository.favorites
    fun filterList(query: String? = ""): LiveData<List<Favorites>> {

        return if (query.isNullOrEmpty()) {
            favoritesRepository.favorites
        } else {
            Transformations.map(favoriteNews) {
                it.filter { favorites ->
                    favorites.title.toLowerCase(Locale.US).contains(query.toLowerCase(Locale.US))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}