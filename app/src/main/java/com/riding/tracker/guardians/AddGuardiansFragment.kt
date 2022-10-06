package com.riding.tracker.guardians

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper

class AddGuardiansFragment  : Fragment() {
    private lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        contentView = inflater.inflate(R.layout.add_guardians, container, false)
        setHasOptionsMenu(true)
        return contentView
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            (activity as AppCompatActivity).supportActionBar?.title = "Add Guardians"
        }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_button, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.save_button -> {
                saveGuardian()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        private fun saveGuardian() {

            val emailAddressEditText = contentView.findViewById<TextInputEditText>(R.id.email_address)
            val nameEditText = contentView.findViewById<TextInputEditText>(R.id.edit_name)
            val phoneEditText = contentView.findViewById<TextInputEditText>(R.id.phone_number)

            DatabaseHelper.updateProfile(
                name = nameEditText.text.toString(),
                emailAddress = emailAddressEditText.text.toString(),
                phoneNumber = phoneEditText.text.toString()
            )
        }

}