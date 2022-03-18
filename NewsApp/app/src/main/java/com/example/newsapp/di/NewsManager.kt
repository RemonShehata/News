package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.data.repos.UserRepositoryImp
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.data.room.NewsDatabaseFactory
import com.example.newsapp.data.room.UserDao

object NewsManager {

    private lateinit var database: NewsDatabase
    private lateinit var userDao: UserDao
    lateinit var userRepo: UserRepository

    fun initialize(
        context: Context
    ){
        database = NewsDatabaseFactory.buildNewsDatabaseProvider(context)
        userDao = database.userDao()
        userRepo = UserRepositoryImp(userDao)
    }
}