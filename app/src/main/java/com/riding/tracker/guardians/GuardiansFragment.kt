package com.riding.tracker.guardians


import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper


class GuardiansFragment : Fragment() {
    private lateinit var contentView: View

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
        recyclerView.adapter = GuardiansAdapter(guardians)
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
                //-- Extra step
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

    private fun getContactInformation(id: String) {
        var cursor: Cursor?
        var guardianName: String?
        var guardianPhone: String?
        var guardianEmail: String?


        try {
            cursor = activity?.contentResolver?.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.CONTACT_ID + "=?",
                arrayOf(id),
                null
            )
            if (cursor?.moveToFirst() == true) {
                val columnIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
                if (columnIndex >= 0) {
                    guardianName = cursor.getString(columnIndex)
                    Log.i("GuardiansFragment", "Result: $guardianName")
                }
            }
            if (cursor?.moveToFirst() == true) {
                val columnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA)
                if (columnIndex >= 0) {
                    guardianPhone = cursor.getString(columnIndex)
                    Log.i("GuardiansFragment", "Result: $guardianPhone")
                }
            }
            if(cursor?.moveToFirst() == true) {
                val columnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                if (columnIndex >= 0) {
                    guardianEmail = cursor.getString(columnIndex)
                    Log.i("GuardiansFragment", "Result: $guardianEmail")
                }
            }

            cursor?.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
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
        }
        return super.onOptionsItemSelected(item)
    }
}
