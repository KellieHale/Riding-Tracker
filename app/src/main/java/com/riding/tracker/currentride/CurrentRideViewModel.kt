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

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val _latitude = MutableLiveData<Location>()
    val latitude: LiveData<Location>
        get() = _latitude

    private val _longitude = MutableLiveData<Location>()
    val longitude: LiveData<Location>
        get() = _longitude

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

    private fun updateLocationUI(context: Context){
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(
            context,
            intent,
            Bundle()
        )
    }

    fun showLocationPermissionErrorDialog(context: Context?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.location_permission_error_title)
        builder.setMessage(R.string.location_permission_error_message)
        builder.setPositiveButton(R.string.allow) { dialog, _ ->
            context?.let { updateLocationUI(it) }
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.deny, null)
        builder.setNeutralButton(R.string.cancel, null)
        builder.show()
    }

    fun aboutDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.about_title)
        builder.setMessage(R.string.about_message)
        builder.setNeutralButton(R.string.ok, null)
        builder.show()
    }

}