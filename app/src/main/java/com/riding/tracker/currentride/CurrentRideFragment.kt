package com.riding.tracker.currentride

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.riding.tracker.R
import com.riding.tracker.databinding.CurrentRideFragmentBinding


class CurrentRideFragment : Fragment(), OnMapReadyCallback {


    private val viewModel: CurrentRideViewModel by lazy {
        ViewModelProvider(this)[CurrentRideViewModel::class.java]
    }

    private lateinit var binding: CurrentRideFragmentBinding

    private lateinit var map: GoogleMap

    private lateinit var contentView: View

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var homeLatLng = LatLng(latitude, longitude)

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getDeviceLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getDeviceLocation()
            } else ->{
                viewModel.showLocationPermissionErrorDialog(context)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = CurrentRideFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        contentView = inflater.inflate(R.layout.maplayout, container, false)
        setHasOptionsMenu(true)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getDeviceLocation()

        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.current_ride)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
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
        map = googleMap
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))
        checkForLocationPermission()
        getDeviceLocation()
    }

    private fun checkForLocationPermission(): Boolean {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                return isLocationEnabled()
            }
            else -> {
                locationPermissionRequest.
                    launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
        return checkForLocationPermission()
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().
            getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        if (isLocationEnabled()) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    val currentPosition = LatLng(latitude, longitude)
                    map.addMarker(MarkerOptions().position(currentPosition))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15f))
                }
                .addOnFailureListener {
                    viewModel.showLocationPermissionErrorDialog(context)
                }
        }
    }

}