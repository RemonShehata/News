package com.example.newsapp.data.repos

import com.example.newsapp.data.network.News

class NewsRepository: NewsRepo {

    override suspend fun getNews(): News {
        TODO("Not yet implemented")
    }
}
