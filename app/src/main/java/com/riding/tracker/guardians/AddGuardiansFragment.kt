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

    private fun saveGuardian() {
        val guardianNameEditText = contentView.findViewById<TextInputEditText>(R.id.guardian_name)
        val guardianPhoneEditText = contentView.findViewById<TextInputEditText>(R.id.guardian_phone)
        val guardianEmailEditText = contentView.findViewById<TextInputEditText>(R.id.guardian_email)

        DatabaseHelper.addGuardian(
            name = guardianNameEditText.text.toString(),
            phoneNumber = guardianPhoneEditText.text.toString(),
            email = guardianEmailEditText.text.toString()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_button -> {
                saveGuardian()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_button, menu)
    }
}