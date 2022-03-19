package com.example.newsapp.features.news

import androidx.lifecycle.*
import com.example.newsapp.data.network.Response
import com.example.newsapp.data.repos.NewsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepo: NewsRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val newsDataMutableLiveData = MutableLiveData<Response>()

    val newsLiveData: LiveData<Response>
        get() = newsDataMutableLiveData

    fun loadNewsData() {
        newsDataMutableLiveData.value = Response.Loading
        viewModelScope.launch(dispatcher) {
            val result = newsRepo.getTopHeadlines()
            newsDataMutableLiveData.postValue(result)
        }
    }

    fun updateNewsDataFromApi() {
        newsDataMutableLiveData.value = Response.Loading
        viewModelScope.launch(dispatcher) {
            val result = newsRepo.updateNewsTopHeadlines()
            newsDataMutableLiveData.postValue(result)
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