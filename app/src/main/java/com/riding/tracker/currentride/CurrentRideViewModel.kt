package com.riding.tracker.currentride

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.riding.tracker.R

class CurrentRideViewModel: ViewModel() {


    private val _latitude = MutableLiveData<Location>()
    val latitude: LiveData<Location>
        get() = _latitude

    private val _longitude = MutableLiveData<Location>()
    val longitude: LiveData<Location>
        get() = _longitude

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation


    fun aboutDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.about_title)
        builder.setMessage(R.string.about_message)
        builder.setNeutralButton(R.string.ok, null)
        builder.show()
    }

}