package com.riding.tracker

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riding.tracker.roomdb.AppDatabase
import com.riding.tracker.roomdb.DatabaseHelper


class MainActivity : AppCompatActivity() {

//    private lateinit var contentView: View

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_host_fragment)
        navigationBar.setupWithNavController(navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.current_ride)

        DatabaseHelper.setupDatabase(applicationContext)
    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup,
//        savedInstanceState: Bundle?
//
//    ): View {
////        contentView = inflater.inflate(R.layout.settings, container, false)
//        return contentView
//    }
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////
////        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.settings)
////    }
}

