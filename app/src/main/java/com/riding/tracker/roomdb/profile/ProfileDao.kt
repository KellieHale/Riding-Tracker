package com.riding.tracker.roomdb.profile

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE profileId = :profileId")
    fun getProfile(profileId: Int): Profile?

    @Update
    fun updateProfile(vararg profile: Profile)

    @Delete
    fun delete(profile: Profile)

    @Insert(onConflict = REPLACE)
    fun insert(profile: Profile)
}