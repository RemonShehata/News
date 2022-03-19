package com.example.newsapp.data.repos

import com.example.newsapp.data.entities.NewsEntity

interface NewsRepo {

    suspend fun getNews(): NewsEntity
}
