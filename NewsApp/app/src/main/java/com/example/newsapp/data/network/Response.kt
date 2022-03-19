package com.example.newsapp.data.network

sealed class Response {
    object Loading: Response()
    data class Success(val data: News) : Response()
    data class Failure(val reason: FailureReason)
}

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class Source(
    val id: String?,
    val name: String
)

sealed class FailureReason {
    object NoInternet : FailureReason()
}
