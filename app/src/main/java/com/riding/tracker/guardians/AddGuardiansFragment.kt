package com.riding.tracker.guardians


import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper
import com.riding.tracker.roomdb.guardians.Guardian

class AddGuardiansFragment : Fragment() {

    private lateinit var contentView: View

    private var guardian: Guardian? = null

    private lateinit var guardianNameEditText: TextInputEditText
    private lateinit var guardianPhoneEditText: TextInputEditText
    private lateinit var guardianEmailEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        contentView = inflater.inflate(R.layout.add_guardians, container, false)
        setHasOptionsMenu(true)
        guardian = arguments?.getParcelable("guardian")
        guardianNameEditText = contentView.findViewById(R.id.guardian_name)
        guardianPhoneEditText = contentView.findViewById(R.id.guardian_phone)
        guardianEmailEditText = contentView.findViewById(R.id.guardian_email)

        guardian?.let {
            guardianNameEditText.setText(it.name)
            guardianPhoneEditText.setText(it.phoneNumber)
            guardianEmailEditText.setText(it.emailAddress)
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Add Guardians"
    }

    private fun saveGuardian() {
        val name = guardianNameEditText.text.toString()
        val phoneNumber = guardianPhoneEditText.text.toString()
        val email = guardianEmailEditText.text.toString()
        if (guardian != null) {
            guardian?.let { editedGuardian ->
                editedGuardian.name = name
                editedGuardian.phoneNumber = phoneNumber
                editedGuardian.emailAddress = email
                DatabaseHelper.editGuardian(editedGuardian)
            }
        } else {
            DatabaseHelper.addGuardian(
                name = name,
                phoneNumber = phoneNumber,
                email = email
            )
        }
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