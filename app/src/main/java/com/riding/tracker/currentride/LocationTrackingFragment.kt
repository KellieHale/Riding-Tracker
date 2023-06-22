package com.riding.tracker.currentride

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class LocationTrackingFragment(val context: Context) {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var startedLocationTracking = false
    private val locationRequest = LocationRequest()

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var homeLatLng = LatLng(latitude, longitude)

    private lateinit var locationCallback: LocationCallback



    init {
        configureLocationRequest()
        setupLocationClient()
    }

    private fun setupLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    homeLatLng = LatLng(location.latitude, location.longitude)
                }
            }
        }
    }


    private fun configureLocationRequest() {
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLIS
        locationRequest.fastestInterval = FASTEST_MILLIS_UPDATE
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    fun trackLocation(locationCallback: LocationCallback){
        if (!startedLocationTracking) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
            startedLocationTracking = true
        }
    }

    fun stopTrackingLocation() {
        if (startedLocationTracking) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    companion object {
        const val UPDATE_INTERVAL_IN_MILLIS: Long = 0
        const val FASTEST_MILLIS_UPDATE = UPDATE_INTERVAL_IN_MILLIS / 2
    }



}