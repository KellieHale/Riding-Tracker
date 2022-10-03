package com.riding.tracker.guardians

import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R

//-- class GuardiansAdapter(val guardians: List<Guardian>)
class GuardiansAdapter : RecyclerView.Adapter<GuardiansAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView
        val broadcastSwitch: Switch

        init {
            nameTextView = itemView.findViewById(R.id.name_text_view)
            broadcastSwitch = itemView.findViewById(R.id.broadcastSwitch)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
        //-- Inflate guardian_row.xml for each guardian
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
        //-- Get each guardian (val guardian = guardians[position])
        //-- nameTextView.text = guardian.name
        //-- broadcastSwitch.toggle = guardian.broadcast
    }

    override fun getItemCount(): Int {
        //-- return guardians.size
        TODO("Not yet implemented")
    }
}