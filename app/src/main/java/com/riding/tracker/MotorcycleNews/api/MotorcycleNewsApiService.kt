package com.riding.tracker.motorcycleNews.api

import androidx.lifecycle.LiveData
import com.riding.tracker.roomdb.motorcycleNews.Feed
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

interface MotorcycleNewsApiService {
    val feedLiveData: LiveData<Feed>

    suspend fun fetchFeed(id: String)

}