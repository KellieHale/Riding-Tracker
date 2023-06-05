package com.riding.tracker.motorcycleNews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riding.tracker.motorcycleNews.api.motorcycleNewsItems.Articles
import com.riding.tracker.motorcycleNews.api.NewsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MotorcycleNewsViewModel: ViewModel() {

    private val repository = NewsRepository()

    val onArticlesUpdated =  MutableLiveData<List<Articles>?>()

    fun fetchFeed() {
        viewModelScope.launch {
            val feed = repository.fetchFeed()
            onArticlesUpdated.postValue(feed.articleList)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
    }

}