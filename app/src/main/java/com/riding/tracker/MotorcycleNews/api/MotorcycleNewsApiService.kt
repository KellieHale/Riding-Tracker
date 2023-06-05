package com.riding.tracker.motorcycleNews.api

import com.riding.tracker.roomdb.motorcycleNews.Feed
import retrofit2.http.GET

interface MotorcycleNewsApiService {
    @GET("arcio/rss")
    suspend fun fetchFeed(): Feed
}