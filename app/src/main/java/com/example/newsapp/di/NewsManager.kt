package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.network.NewsApi
import com.example.newsapp.data.repos.NewsRepo
import com.example.newsapp.data.repos.NewsRepository
import com.example.newsapp.data.repos.UserRepo
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.data.room.NewsDao
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.data.room.NewsDatabaseFactory
import com.example.newsapp.data.room.UserDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object NewsManager {

    lateinit var moshi: Moshi
    private lateinit var database: NewsDatabase
    private lateinit var userDao: UserDao
    lateinit var userRepo: UserRepo
    private lateinit var newsDao: NewsDao
    private lateinit var newsApi: NewsApi
    lateinit var newsRepo: NewsRepo

    fun initialize(
        context: Context
    ) {

        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        database = NewsDatabaseFactory.buildNewsDatabaseProvider(context)
        userDao = database.userDao()
        userRepo = UserRepository(userDao)
        newsDao = database.newsDao()
        newsApi = NewsApi.create()
        newsRepo = NewsRepository(newsApi, newsDao)
    }
}
