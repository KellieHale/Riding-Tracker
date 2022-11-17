package com.riding.tracker.guardians


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
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
        inflater.inflate(R.menu.add_guardian, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.add_guardian -> {
                findNavController().navigate(R.id.startAddGuardiansFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
