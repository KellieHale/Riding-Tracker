package com.riding.tracker.currentride

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riding.tracker.NotificationUtil
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

    private val notificationUtil = NotificationUtil()
    private val sosNotificationUtil = SosNotificationUtil()


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

        currentRideObservers()

        return contentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.current_ride)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val fab: FloatingActionButton = view.findViewById(R.id.location_button)
        fab.setOnClickListener {
            cameraToCurrentLocation()
        }
        binding.sosButton.setOnClickListener {
            sendSosMessage()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
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
        R.id.about -> {
            viewModel.aboutDialog(requireContext())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))

    }

    override fun onResume() {
        super.onResume()
        currentRideObservers()
    }

    private fun currentRideObservers() {
        notificationUtil.onLocationPermissionsUpdated.observe(viewLifecycleOwner) {allGranted ->
            if (allGranted) {
                getDeviceLocation()
            }
        }
//        notificationUtil.onSosPermissionsUpdated.observe(viewLifecycleOwner) {allGranted ->
//            if (allGranted) {
//                sendSosMessage()
//            }
//        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        if (notificationUtil.areLocationPermissionsGranted(requireContext())) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    val currentPosition = LatLng(latitude, longitude)
                    map.addMarker(MarkerOptions().position(currentPosition))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15f))
                }
        } else {
            notificationUtil.requestLocationPermissions(this)
        }
    }

    private fun cameraToCurrentLocation(){
            getDeviceLocation()
            map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))
    }


    private fun sendSosMessage(){
        if (notificationUtil.areSosPermissionsGranted(requireContext())) {
            sosNotificationUtil.callGuardian()
            sosNotificationUtil.textGuardian()
            sosNotificationUtil.notifyGuardian()
        } else {
            notificationUtil.requestSosPermissions(this)
        }
    }



}