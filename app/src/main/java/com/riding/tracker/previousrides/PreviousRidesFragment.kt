package com.riding.tracker.previousrides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.riding.tracker.R

class PreviousRidesFragment : Fragment(){

    private lateinit var contentView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        contentView = inflater.inflate(R.layout.previous_ride_fragment, container, false)

        return contentView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.previous_rides)
    }
}