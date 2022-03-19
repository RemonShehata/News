package com.example.newsapp.data.repos

import com.example.newsapp.data.network.News

interface NewsRepo {

    suspend fun getNews(): News
}
