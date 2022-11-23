package com.riding.tracker.guardians


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
            val data: Intent? = result.data



            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes

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
                showImportContactsError()
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

    private fun showImportContactsError() {
        //-- Show Error Dialog
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.import_contact -> {
                checkForContactsPermission()
            }
            R.id.add_guardian -> {
//                findNavController().navigate(R.id.startAddGuardiansFragment)

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
