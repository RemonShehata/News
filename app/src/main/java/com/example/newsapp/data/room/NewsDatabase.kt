package com.example.newsapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.entities.NewsConverter
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.entities.UserEntity

@Database(
    entities = [UserEntity::class, NewsEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    NewsConverter::class
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
}

object NewsDatabaseFactory {
    @Volatile
    private var instance: NewsDatabase? = null

    fun buildNewsDatabaseProvider(context: Context): NewsDatabase {
        return instance ?: kotlin.run {
            synchronized(this) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "news_database"
                ).build()
                instance!!
            }
        }
    }
}
