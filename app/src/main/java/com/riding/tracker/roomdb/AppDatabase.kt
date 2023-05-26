package com.riding.tracker.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riding.tracker.roomdb.guardians.Guardian
import com.riding.tracker.roomdb.guardians.GuardianDao
import com.riding.tracker.roomdb.motorcycleNews.Articles
import com.riding.tracker.roomdb.motorcycleNews.MotorcycleNewsDao
import com.riding.tracker.roomdb.profile.Profile
import com.riding.tracker.roomdb.profile.ProfileDao

@Database(entities = [Profile::class, Guardian::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun guardianDao(): GuardianDao
}
