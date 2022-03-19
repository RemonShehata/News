package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.entities.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsEntity): Long

    @Query("SELECT * FROM NewsEntity")
    suspend fun getNews(): NewsEntity

    @Query("SELECT COUNT(id) FROM NewsEntity")
    suspend fun getNewsCount() : Long
}