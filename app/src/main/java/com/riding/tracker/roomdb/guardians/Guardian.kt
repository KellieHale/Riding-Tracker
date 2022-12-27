package com.riding.tracker.roomdb.guardians

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Guardian(
    @PrimaryKey(autoGenerate = true) val guardianId: Int? = null,
    @ColumnInfo(name = "guardian_name") var name: String?,
    @ColumnInfo(name = "guardian_phone") var phoneNumber: String?,
    @ColumnInfo(name = "guardian_email") var emailAddress: String? = null,
    @ColumnInfo(name = "guardian_broadcast") val broadcast: Boolean = true
//--broadcast is the toggle switch for activating guardians
) : Parcelable
