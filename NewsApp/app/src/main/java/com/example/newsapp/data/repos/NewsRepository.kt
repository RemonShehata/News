package com.example.newsapp.data.repos

import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.NewsApi
import com.example.newsapp.data.room.NewsDao
import com.example.newsapp.utils.convertToEntity

class NewsRepository(private val api: NewsApi, private val newsDao: NewsDao) : NewsRepo {

    override suspend fun getNews(): NewsEntity {
        val news = api.getEverything()
        val newsEntity = news.convertToEntity()
        newsDao.insertNews(newsEntity)
        return newsEntity
    }
}
