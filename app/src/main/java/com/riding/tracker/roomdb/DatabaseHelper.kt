package com.riding.tracker.roomdb

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Room
import com.riding.tracker.roomdb.profile.Profile

object DatabaseHelper {

    private lateinit var db: AppDatabase

    fun setupDatabase(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries()
            .build()
    }

    fun getProfile(): Profile? {
        return db.profileDao().getProfile(1)
    }

    fun updateProfile(
        name: String?,
        emailAddress: String?,
        phoneNumber: String?,
        fullAddress: String?
    ) {
        db.profileDao().insert(
            Profile(
                1,
                name,
                emailAddress,
                phoneNumber,
                fullAddress
            )
        )
    }
}