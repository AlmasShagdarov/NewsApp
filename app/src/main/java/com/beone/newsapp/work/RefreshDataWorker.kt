package com.beone.newsapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.beone.newsapp.database.getDatabase
import com.beone.newsapp.repository.*

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        return try {
            val database = getDatabase(applicationContext)
            BusinessNewsRepository(database).refreshNews()
            NewsRepository(database).refreshNews()
            EntertainmentNewsRepository(database).refreshNews()
            HealthNewsRepository(database).refreshNews()
            ScienceNewsRepository(database).refreshNews()
            SportsNewsRepository(database).refreshNews()
            TechnologyNewsRepository(database).refreshNews()
            Result.success()
        } catch (e: Throwable) {
            Result.retry()
        }
    }
}