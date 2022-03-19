package com.example.newsapp.utils

import com.example.newsapp.data.entities.ArticleEntity
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.Article
import com.example.newsapp.data.network.News

fun News.convertToEntity(): NewsEntity {
    val articles: MutableList<ArticleEntity> = mutableListOf<ArticleEntity>()
    this.articles.forEach { articles.add(it.convertToEntity()) }
    return NewsEntity(
        articles = articles
    )
}

fun Article.convertToEntity(): ArticleEntity {
    return ArticleEntity(
        sourceId = this.source.id,
        sourceName = this.source.name,
        this.author,
        this.title,
        this.url,
        this.urlToImage,
        this.publishedAt,
        this.content
    )
}
