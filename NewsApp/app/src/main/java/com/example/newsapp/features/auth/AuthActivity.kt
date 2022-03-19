package com.example.newsapp.features.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.data.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        lifecycleScope.launch(Dispatchers.IO){
            val api = NewsApi.create()
            val news = api.getEverything()
            Log.d("Remon", "onCreate: $news")
        }
    }
}