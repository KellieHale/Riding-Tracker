package com.riding.tracker.roomdb.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile (
    @PrimaryKey val profileId: Int,
    @ColumnInfo(name = "full_name") val name: String?,
    @ColumnInfo(name = "email_address") val emailAddress: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "full_address") val fullAddress: String?
)