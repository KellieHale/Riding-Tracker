package com.riding.tracker.roomdb.motorcycleNews

import androidx.room.*
import org.simpleframework.xml.Path
import retrofit2.http.GET


interface MotorcycleNewsDao {
    @GET("/Integration/StoryRss{id}.xml")
    suspend fun getFeed(@Path("id") id: String): Feed
}
