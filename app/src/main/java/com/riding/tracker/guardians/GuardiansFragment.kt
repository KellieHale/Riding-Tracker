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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper


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

        val guardians = DatabaseHelper.getAllGuardians()
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = GuardiansAdapter(guardians)
        recyclerView.adapter = adapter

        addDivider(recyclerView)
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


    private fun showContactsPermissionErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.permission_denied))
            builder.setMessage(getString(R.string.contacts_error_dialogue))
            builder.setPositiveButton(getString(R.string.ok), null)
        builder.show()
    }

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

        //-- Check for empty name, phone number, email address
        //-- check for proper name (only a-z characters)
        //-- check for improper phone numbers (only 1-9)
        //-- Check for proper email address (name @ domain. com/edu/org, etc)
        DatabaseHelper.addGuardian(name, phoneNumber, emailAddress)
        val guardians = DatabaseHelper.getAllGuardians()
        adapter.setGuardians(guardians)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.import_contact -> {
                checkForContactsPermission()
            }
            R.id.add_guardian -> {
                findNavController().navigate(R.id.startAddGuardiansFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
