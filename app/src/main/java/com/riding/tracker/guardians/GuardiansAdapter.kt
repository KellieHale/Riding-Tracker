package com.riding.tracker.guardians


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.roomdb.DatabaseHelper
import com.riding.tracker.roomdb.guardians.Guardian

class GuardiansAdapter(
    val onItemLongPressed: (Guardian) -> Unit,
    val onItemClicked: (Guardian) -> Unit):
    RecyclerView.Adapter<GuardiansAdapter.ViewHolder>() {

    private lateinit var guardians: List<Guardian>

    init {
        updateGuardians()
    }

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
            //-- use onItemClicked to pass guardian back
        }
        holder.itemView.setOnLongClickListener {
            val currentGuardian = guardians[position]
            onItemLongPressed(currentGuardian)
            true
        }
    }

    fun updateGuardians() {
        guardians = DatabaseHelper.getAllGuardians()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return guardians.size
    }
}