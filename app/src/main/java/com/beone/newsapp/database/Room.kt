package com.beone.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DatabaseTopNews::class, Favorites::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val topNewsDao: TopNewsDao
    abstract val favoritesDao: FavoritesDao
}

private lateinit var INSTANCE: NewsDatabase

fun getDatabase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}