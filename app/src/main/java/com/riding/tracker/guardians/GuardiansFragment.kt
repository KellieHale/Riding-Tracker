package com.riding.tracker.guardians


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import android.provider.ContactsContract.Data
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper
import com.riding.tracker.roomdb.DatabaseHelper.deleteGuardian
import com.riding.tracker.roomdb.guardians.Guardian


class GuardiansFragment : Fragment() {

    private lateinit var contentView: View
    private lateinit var adapter: GuardiansAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        contentView = inflater.inflate(R.layout.guardians, container, false)
        setHasOptionsMenu(true)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.guardians)
        val recyclerView: RecyclerView = contentView.findViewById(R.id.guardians_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = GuardiansAdapter(onItemClicked = { guardian ->
            editGuardian(guardian)
        }, onItemLongPressed = { guardian ->
            guardian.guardianId?.let { guardianId ->
                showDeleteGuardianDialog(guardianId)
            }
        })
        recyclerView.adapter = adapter
        addDivider(recyclerView)
    }

    private fun showDeleteGuardianDialog(guardianId: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete_guardian_title))
        builder.setMessage(getString(R.string.delete_guardian_message))
        builder.setPositiveButton(getString(R.string.delete)) { _, _ ->
            deleteGuardian(guardianId)
            adapter.updateGuardians()
        }
        builder.setNeutralButton(getString(R.string.cancel), null)
        builder.show()
    }

    private fun addDivider(recyclerView: RecyclerView) {
        val verticalDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        val verticalDivider = ContextCompat.getDrawable(requireActivity(), R.drawable.vertical_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        recyclerView.addItemDecoration(verticalDecoration)
    }

    private fun editGuardian(guardian: Guardian) {
        val bundle = bundleOf("guardian" to guardian)
        findNavController().navigate(R.id.startAddGuardiansFragment, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.import_contact, menu)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Uri? = result.data?.data
            data?.lastPathSegment?.let { id: String ->
                getContactInformation(id)
            }
        }

    private fun showContacts() {
        val contactPickerIntent = Intent(
            Intent.ACTION_PICK,
            ContactsContract.Contacts.CONTENT_URI
        )
        resultLauncher.launch(contactPickerIntent)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showContacts()
            } else {
                showContactsPermissionErrorDialog()
            }
        }

    private fun checkForContactsPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                showContacts()
            }
            shouldShowRequestPermissionRationale("") -> {
                showImportContactError()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS)
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun showContactsPermissionErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.permission_denied))
            builder.setMessage(getString(R.string.contacts_error_dialogue))
            builder.setPositiveButton(getString(R.string.ok), null)
        builder.show()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun showImportContactError() {
        val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.import_contact_error))
            builder.setMessage(getString(R.string.import_contact_error_dialogue))
            builder.setPositiveButton(getString(R.string.ok), null)
        builder.show()
    }

    @SuppressLint("Range")
    private fun getContactInformation(id: String) {
        val cursor: Cursor?
        var guardianName  = ""
        var guardianPhone = ""
        var guardianEmail = ""

        try {
            cursor = activity?.contentResolver?.query(
                Data.CONTENT_URI,
                null,
                Data.CONTACT_ID + "=?",
                arrayOf(id),
                null
            )
            if (cursor?.moveToFirst() == true) {
                val columnName = cursor.getColumnIndex(Data.DISPLAY_NAME)
                val columnEmail = cursor.getColumnIndex(CommonDataKinds.Email.ADDRESS)
                if (columnName >= 0) {
                    guardianName = cursor.getString(columnName)
                }
                if (columnEmail >= 0) {
                    guardianEmail = cursor.getString(columnEmail)
                }

                val hasPhone: String? =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhone.equals("1", ignoreCase = true)) {
                    val phones: Cursor? = activity?.contentResolver?.query(
                        CommonDataKinds.Phone.CONTENT_URI, null,
                        CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
                    )
                    phones?.moveToFirst()
                    val contactNumber =
                        phones?.getString(phones.getColumnIndex(CommonDataKinds.Phone.NUMBER))
                    contactNumber?.let { phoneNumber -> guardianPhone = phoneNumber }
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        saveGuardian(guardianName, guardianPhone, guardianEmail)
    }

    private fun saveGuardian(
        name: String,
        phoneNumber: String,
        emailAddress: String) {
        val validPhoneNumber = InputValidator.isValidPhoneNumber(phoneNumber)
        var email = emailAddress
        if (name.isEmpty() || phoneNumber.isEmpty() || !InputValidator.isNameValid(name) || !validPhoneNumber) {
            showImportContactError()
            return
        } else if (!InputValidator.isEmailValid(emailAddress)) {
            email = ""
        }
        //-- check for improper phone numbers (only 1-9)
        //-- Check for proper email address (name @ domain. com/edu/org, etc)
        if (!DatabaseHelper.guardianExists(name, phoneNumber, email)) {
            DatabaseHelper.addGuardian(name, phoneNumber, email)
            adapter.updateGuardians()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.import_contact -> {
                checkForContactsPermission()
            }
            R.id.add_guardian -> {
                findNavController().navigate(R.id.startAddGuardiansFragment)
            }
            R.id.guardian_switch -> {
                // call function for guardianSwitch
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun guardianSwitch(item: MenuItem): Boolean {
        //set master switch to toggle all on or off and if off still be able to switch on individually
    }
}