package com.riding.tracker.motorcyclenews.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NetworkHelper {
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
        SimpleXmlConverterFactory.createNonStrict(
            Persister(AnnotationStrategy())
        )
    }
}