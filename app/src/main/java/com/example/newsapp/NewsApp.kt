package com.example.newsapp

import android.app.Application
import android.util.Log
import com.example.newsapp.di.NewsManager

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NewsManager.initialize(context = applicationContext)
    }
}
