package com.riding.tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riding.tracker.roomdb.DatabaseHelper


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_host_fragment)
        navigationBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ controller: NavController,destination: NavDestination, bundle: Bundle? ->
            if (destination.id == R.id.loginFragment) {
                navigationBar.isVisible = false
            } else {
                if (destination.id == R.id.currentRideFragment||
                    destination.id == R.id.guardiansFragment||
                    destination.id == R.id.profileFragment||
                        destination.id == R.id.motorcycleNewsFragment||
                        destination.id ==R.id.previousRidesFragment) {
                    navigationBar.isVisible = true
                }
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Riding Tracker"

        DatabaseHelper.setupDatabase(applicationContext)
    }

}

