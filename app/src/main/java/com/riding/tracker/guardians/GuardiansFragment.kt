package com.riding.tracker.guardians

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        //-- Get your RecyclerView findViewById
        //-- Create a Guardian data class
        //-- data class Guardian (
        //--    private val name: String
        //--    private val broadcast: Boolean
        //-- )
        //-- Create a new copy of your GuardiansAdapter
        //-- val adapter = GuardiansAdapter
        //-- Bind adapter
        //-- recyclerView.adapter = guardiansAdapter

    }
}