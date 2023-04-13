package com.riding.tracker.currentride

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.riding.tracker.R


class CurrentRideFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: CurrentRideViewModel by lazy {
        ViewModelProvider(this)[CurrentRideViewModel::class.java]
    }
    //-- Need binding possible for ViewModel--//

    private lateinit var map: GoogleMap

    private lateinit var contentView: View
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var longitude = 0.0
    private var latitude = 0.0
    private var homeLatLng = LatLng(latitude, longitude)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        contentView = inflater.inflate(R.layout.maplayout, container, false)
        setHasOptionsMenu(true)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))

        checkForLocationPermission()
    }
    fun checkForLocationPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                getDeviceLocation()
            }
            else -> {
                requestLocationPermissionLauncher.
                    launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
    private fun getDeviceLocation(): (DialogInterface, Int) -> Unit {
        checkForLocationPermission()
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object :
            CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location ->
                latitude = location.latitude
                longitude =location.longitude
            }
        return getDeviceLocation()
    }
    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getDeviceLocation()
            } else {
                viewModel.showLocationPermissionErrorDialog(context)
            }
        }

}