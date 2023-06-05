package com.riding.tracker.motorcycleNews.api

import com.riding.tracker.motorcycleNews.api.motorcycleNewsItems.Feed


class NewsRepository {

    private val apiService = NetworkHelper().motorcycleNewsApiService

    suspend fun fetchFeed(): Feed {
        return apiService.fetchFeed()
    }
}