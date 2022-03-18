package com.example.newsapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.entities.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

object NewsDatabaseFactory {
    private var instance: NewsDatabase? = null

    fun buildNewsDatabaseProvider(context: Context): NewsDatabase {
        return if (instance == null) {
            Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java, "news_database"
            ).build()
        } else instance!!
    }
}