package com.riding.tracker.currentride

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import com.riding.tracker.R

class CurrentRideViewModel: ViewModel() {

    fun aboutDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.about_title)
        builder.setMessage(R.string.about_message)
        builder.setNeutralButton(R.string.ok, null)
        builder.show()
    }

}