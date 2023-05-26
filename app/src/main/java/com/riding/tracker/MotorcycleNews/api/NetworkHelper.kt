package com.riding.tracker.motorcycleNews.api

import com.riding.tracker.motorcycleNews.api.NetworkHelper.Companion.client
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NetworkHelper {
    companion object {
        private val interceptor = HttpLoggingInterceptor()
        private val client: OkHttpClient
        private val retrofit: Retrofit
        val motorcycleNewsApiService: MotorcycleNewsApiService

        init {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            retrofit = Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .build()
            motorcycleNewsApiService = retrofit.create(MotorcycleNewsApiService::class.java)
        }
    }
}