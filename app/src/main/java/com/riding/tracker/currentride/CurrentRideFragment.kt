package com.riding.tracker.currentride

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.riding.tracker.R


class CurrentRideFragment : Fragment(), OnMapReadyCallback {

    private lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        contentView = inflater.inflate(R.layout.maplayout, container, false)
        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.current_ride)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    private fun getDeviceLocation() {
    }

    private fun updateLocationUI() {
    }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getDeviceLocation()
            } else {
                showLocationPermissionErrorDialog()
            }
        }

    private fun checkForLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getDeviceLocation()
                updateLocationUI()
            }
            else -> {
                requestLocationPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun showLocationPermissionErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.location_permission_error_title)
        builder.setMessage(R.string.location_permission_error_message)
//        builder.setPositiveButton(R.string.allow)
        builder.setNegativeButton(R.string.deny, null)
        builder.setNeutralButton(R.string.cancel, null)
        builder.show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}