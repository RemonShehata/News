package com.example.newsapp.network

import com.example.newsapp.BuildConfig
import com.example.newsapp.utils.Keys
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface NewsApi {

    @Headers("X-Api-Key: ${Keys.NEWS_API_KEY}")
    @GET("everything&q=everything")
    suspend fun getEverything(): News


    companion object {
        fun create(): NewsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(Keys.NEWS_API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
                .client(createOkHttpClient())
                .build()
            return retrofit.create(NewsApi::class.java)
        }


        private fun createOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(httpLoggingInterceptor)
            }

            return builder.build()
        }

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}