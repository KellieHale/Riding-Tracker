package com.riding.tracker.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riding.tracker.roomdb.profile.Profile
import com.riding.tracker.roomdb.profile.ProfileDao

@Database(entities = [Profile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}