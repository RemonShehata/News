package com.example.newsapp.data.repos

import com.example.newsapp.data.network.FailureReason
import com.example.newsapp.data.network.NewsApi
import com.example.newsapp.data.network.Response
import com.example.newsapp.data.room.NewsDao
import com.example.newsapp.utils.convertToArticlesList
import com.example.newsapp.utils.convertToEntity
import java.net.UnknownHostException

class NewsRepository(private val api: NewsApi, private val newsDao: NewsDao) : NewsRepo {

    override suspend fun updateNewsTopHeadlines(): Response {
        val news = try {
            api.getTopHeadlines()
        } catch (unknownHost: UnknownHostException) {
            return Response.Failure(FailureReason.NoInternet)
        } catch (e: Exception) {
            return Response.Failure(
                FailureReason.UnknownError(
                    e.localizedMessage ?: "no message in the exception"
                )
            )
        }

        val newsEntity = news.convertToEntity()
        newsDao.insertNews(newsEntity)

        return Response.Success(news.articles)
    }

    override suspend fun getTopHeadlines(): Response {
        val newsCount = newsDao.getNewsCount()
        return if (newsCount > 0) {
            val newsInDB = newsDao.getNews()
            Response.Success(newsInDB.convertToArticlesList())
        } else {
            updateNewsTopHeadlines()
        }
    }
}
