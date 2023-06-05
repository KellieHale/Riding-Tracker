package com.riding.tracker.roomdb.motorcycleNews

import com.riding.tracker.motorcycleNews.api.NetworkHelper


class NewsRepository {

    private val apiService = NetworkHelper().motorcycleNewsApiService

    suspend fun fetchFeed(): Feed {
        return apiService.fetchFeed()
    }
}