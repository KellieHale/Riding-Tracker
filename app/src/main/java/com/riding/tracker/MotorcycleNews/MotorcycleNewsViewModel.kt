package com.riding.tracker.motorcycleNews

import android.content.Context
import androidx.lifecycle.*
import com.riding.tracker.roomdb.motorcycleNews.Articles
import com.riding.tracker.roomdb.motorcycleNews.NewsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MotorcycleNewsViewModel @Inject constructor (private val repository: NewsRepository): ViewModel() {

    val feedLiveData: LiveData<List<Articles>> = Transformations.map(repository.feedLiveData) {items ->
        items?.articleList
    }

    fun fetchFeed(id: String) {
        viewModelScope.launch {
            repository.fetchFeed(id)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
    }

}