package com.riding.tracker.guardians

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.roomdb.guardians.Guardian

class GuardiansAdapter(private var guardians: List<Guardian>, val onItemClicked: (Guardian) -> Unit):
    RecyclerView.Adapter<GuardiansAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView
        val broadcastSwitch: SwitchCompat
        val phoneTextView: TextView
        val emailTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.name_text_view)
            broadcastSwitch = itemView.findViewById(R.id.broadcastSwitch)
            phoneTextView = itemView.findViewById(R.id.phone_text_view)
            emailTextView = itemView.findViewById(R.id.email_text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.guardian_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guardian = guardians[position]
        holder.nameTextView.text = guardian.name
        holder.broadcastSwitch.isChecked = guardian.broadcast
        holder.phoneTextView.text = guardian.phoneNumber
        holder.emailTextView.text = guardian.emailAddress
        holder.itemView.setOnClickListener {
            Log.i("GuardiansAdapter", "onItemClicked")
        }
        holder.itemView.setOnLongClickListener {
            val currentGuardian = guardians[position]
            onItemClicked(currentGuardian)
            true
        }
    }

    override fun getItemCount(): Int {
        return guardians.size
    }

    fun setGuardians(guardians: List<Guardian>) {
        this.guardians = guardians
        notifyDataSetChanged()
    }
}