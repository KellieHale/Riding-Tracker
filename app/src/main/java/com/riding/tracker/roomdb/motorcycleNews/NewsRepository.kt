package com.riding.tracker.roomdb.motorcycleNews

import androidx.lifecycle.MutableLiveData
import com.riding.tracker.motorcycleNews.api.MotorcycleNewsApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsRepository @Inject constructor(
    private val motorcycleNewsDao: MotorcycleNewsDao): MotorcycleNewsApiService {

    override val feedLiveData: MutableLiveData<Feed> = MutableLiveData()

    override suspend fun fetchFeed(id: String) {
        val feed = try {
            motorcycleNewsDao.getFeed(id)
        } catch (cause: Throwable) {
            cause.printStackTrace()
            throw cause
        }
        return   feedLiveData.postValue(feed)
    }
}