package com.riding.tracker.roomdb

import android.content.Context
import androidx.room.Room
import com.riding.tracker.roomdb.guardians.Guardian
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

    fun getProfile(profileId: Int = 1): Profile? {
        return db.profileDao().getProfile(profileId)
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
    fun getGuardian(guardianId: Int): Guardian? {
        return db.guardianDao().getGuardian(guardianId)
    }

    fun guardianExists(name: String, phoneNumber: String?, email: String?) : Boolean {
        return db.guardianDao().guardianExists(name, phoneNumber, email) != null
    }

    fun getAllGuardians(): List<Guardian> {
        return db.guardianDao().getAllGuardians()
    }

    fun deleteGuardian(guardianId: Int) {
        db.guardianDao().delete(guardianId)
    }

    fun editGuardian(guardian: Guardian) {
        db.guardianDao().addOrEditGuardian(guardian)
    }

    fun addGuardian(
        name: String?,
        phoneNumber: String?,
        email: String?,
        broadcast: Boolean = true
    ) {
        val guardian = Guardian(
            name = name,
            phoneNumber = phoneNumber,
            emailAddress = email,
            broadcast = broadcast
        )
        db.guardianDao().addOrEditGuardian(guardian)
    }
}
