package com.riding.tracker.profile

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Database
import com.riding.tracker.MainActivity
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper

class ProfileFragment : Fragment() {

    private lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        contentView = inflater.inflate(R.layout.profile_fragment, container, false)
        setHasOptionsMenu(true)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.profile)

        val profile = DatabaseHelper.getProfile()

        val emailTextView = contentView.findViewById<AppCompatTextView>(R.id.email_address)
        emailTextView.text = profile?.emailAddress
        val phoneTextView = contentView.findViewById<AppCompatTextView>(R.id.phone_number)
        phoneTextView.text = profile?.phoneNumber
        val addressTextView =contentView.findViewById<AppCompatTextView>(R.id.full_address)
        addressTextView.text = profile?.fullAddress
        val nameTextView = contentView.findViewById<AppCompatTextView>(R.id.full_name)
        nameTextView.text = profile?.name
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_profile -> {
                findNavController().navigate(R.id.startEditProfileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}