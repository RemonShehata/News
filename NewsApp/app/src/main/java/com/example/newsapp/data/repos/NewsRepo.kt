package com.example.newsapp.data.repos

import com.example.newsapp.data.network.Response

interface NewsRepo {

    suspend fun updateNewsTopHeadlines(): Response

    suspend fun getTopHeadlines(): Response
}
