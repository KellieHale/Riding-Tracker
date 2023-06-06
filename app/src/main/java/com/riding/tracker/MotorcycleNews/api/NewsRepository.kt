package com.riding.tracker.motorcyclenews.api

import com.riding.tracker.motorcyclenews.api.motorcycleNewsItems.Feed


class NewsRepository {

    private val apiService = NetworkHelper().motorcycleNewsApiService

    suspend fun fetchFeed(): Feed {
        return apiService.fetchFeed()
    }
}