package com.riding.tracker.roomdb.guardians

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Guardian(
    @PrimaryKey(autoGenerate = true) val guardianId: Int? = null,
    @ColumnInfo(name = "guardian_name") val name: String?,
    @ColumnInfo(name = "guardian_phone") val phoneNumber: String?,
    @ColumnInfo(name = "guardian_email") val emailAddress: String?,
    @ColumnInfo(name = "guardian_broadcast") val broadcast: Boolean
)
