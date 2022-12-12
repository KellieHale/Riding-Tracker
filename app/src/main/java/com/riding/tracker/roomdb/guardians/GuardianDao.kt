package com.riding.tracker.roomdb.guardians

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GuardianDao {
    @Query("SELECT * FROM guardian WHERE guardianId = :guardianId")
    fun getGuardian(guardianId: Int): Guardian?

    @Query("SELECT * FROM guardian")
    fun getAllGuardians(): List<Guardian>

    @Update
    fun addGuardian(vararg guardian: Guardian)

    @Query("DELETE FROM guardian WHERE guardian_phone = :phoneNumber")
    fun delete(phoneNumber: String)

    @Insert(onConflict = REPLACE)
    fun insert(guardian: Guardian)
}