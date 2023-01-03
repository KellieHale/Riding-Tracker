package com.riding.tracker.currentride

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.current_ride)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(map: GoogleMap) {
        map.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }

    private fun showLocation() {


    }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showLocation()
            } else {
                showLocationPermissionErrorDialog()
            }
        }

    private fun checkForLocationPermission(){
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission_group.LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                showLocation()
            }
            else -> {
                requestLocationPermissionLauncher.launch(
                    Manifest.permission_group.LOCATION
                )
            }
        }
    }

    private fun showLocationPermissionErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.location_permission_error_title)
        builder.setMessage(R.string.location_permission_error_message)
        builder.setPositiveButton(R.string.allow, requestLocationPermissionLauncher)
        builder.setNegativeButton(R.string.deny, null)
        builder.setNeutralButton(R.string.cancel, null)
        builder.show()
    }
}