package com.riding.tracker.currentride

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    private fun onConnected(p0: Int) {
        TODO("Not yet implemented")
    }

    private fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    private fun onConnectionFailed(p0: Int) {
        TODO("Not yet implemented")
    }

    private fun updateLocationUI(): (DialogInterface, Int) -> Unit {
        onConnected(0)
        onConnectionSuspended(0)
        onConnectionFailed(0)

        return updateLocationUI()
    }

    fun showLocationPermissionErrorDialog(context: Context?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.location_permission_error_title)
        builder.setMessage(R.string.location_permission_error_message)
        builder.setPositiveButton(R.string.allow, DialogInterface.OnClickListener(updateLocationUI()))
        builder.setNegativeButton(R.string.deny, null)
        builder.setNeutralButton(R.string.cancel, null)
        builder.show()
    }



}