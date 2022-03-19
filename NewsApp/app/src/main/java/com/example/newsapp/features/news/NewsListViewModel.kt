package com.example.newsapp.features.news

import androidx.lifecycle.*
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.repos.NewsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepo: NewsRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val newsDataMutableLiveData = MutableLiveData<NewsEntity>()

    val newsLiveData: LiveData<NewsEntity>
        get() = newsDataMutableLiveData

    fun loadNewsData() {
        viewModelScope.launch(dispatcher) {
            val news = newsRepo.getNews()
            newsDataMutableLiveData.postValue(news)
        }
    }
}

class MyViewModelFactory(
    private val newsRepo: NewsRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(newsRepo, ioDispatcher) as T
    }
}