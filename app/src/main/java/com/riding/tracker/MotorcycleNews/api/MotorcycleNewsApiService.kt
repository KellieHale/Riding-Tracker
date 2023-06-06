package com.riding.tracker.motorcyclenews.api

import com.riding.tracker.motorcyclenews.api.motorcycleNewsItems.Feed
import retrofit2.http.GET

interface MotorcycleNewsApiService {
    @GET("arcio/rss")
    suspend fun fetchFeed(): Feed
}