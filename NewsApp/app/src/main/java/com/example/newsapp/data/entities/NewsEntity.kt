package com.example.newsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val articles: List<ArticleEntity>
)

data class ArticleEntity(
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

class NewsConverter {
    companion object {

        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val ArticlesType =
            Types.newParameterizedType(List::class.java, ArticleEntity::class.java)
        private val articlesAdapter = moshi.adapter<List<ArticleEntity>>(ArticlesType)


        @TypeConverter
        @JvmStatic
        fun articlesToString(articles: List<ArticleEntity>): String? =
            articlesAdapter.toJson(articles)


        @TypeConverter
        @JvmStatic
        fun stringToArticles(articles: String): List<ArticleEntity> =
            articlesAdapter.fromJson(articles).orEmpty()
    }
}

