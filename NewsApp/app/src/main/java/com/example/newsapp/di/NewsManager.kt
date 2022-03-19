package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.network.NewsApi
import com.example.newsapp.data.repos.NewsRepo
import com.example.newsapp.data.repos.NewsRepository
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.data.repos.UserRepositoryImp
import com.example.newsapp.data.room.NewsDao
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.data.room.NewsDatabaseFactory
import com.example.newsapp.data.room.UserDao

object NewsManager {

    private lateinit var database: NewsDatabase
    private lateinit var userDao: UserDao
    lateinit var userRepo: UserRepository
    private lateinit var newsDao: NewsDao
    private lateinit var newsApi: NewsApi
    lateinit var newsRepo: NewsRepo

    fun initialize(
        context: Context
    ) {
        database = NewsDatabaseFactory.buildNewsDatabaseProvider(context)
        userDao = database.userDao()
        userRepo = UserRepositoryImp(userDao)
        newsDao = database.NewsDao()
        newsApi = NewsApi.create()
        newsRepo = NewsRepository(newsApi, newsDao)
    }
}