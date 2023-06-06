package com.riding.tracker.motorcyclenews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riding.tracker.motorcyclenews.api.NewsRepository
import com.riding.tracker.motorcyclenews.api.motorcycleNewsItems.Articles
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