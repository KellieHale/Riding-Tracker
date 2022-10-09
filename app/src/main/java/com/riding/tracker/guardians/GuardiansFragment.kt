package com.riding.tracker.guardians

import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riding.tracker.R

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

        val dummyGuardians = ArrayList<Guardian>()
        dummyGuardians.add(
            Guardian(
                name = "Joshua Hale",
                phoneNumber = "864-395-4095",
                emailAddress = "joshua.kainoa.hale@gmail.com",
                broadcast = true
            )
        )
        dummyGuardians.add(
            Guardian(
                name = "Elsie Tracy",
                phoneNumber = "707-227-0948",
                emailAddress = "chef1emt@yahoo.com",
                broadcast = true
            )
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = GuardiansAdapter(dummyGuardians)
    }

}