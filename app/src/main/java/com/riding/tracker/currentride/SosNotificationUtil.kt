package com.riding.tracker.currentride

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.riding.tracker.roomdb.guardians.Guardian


class SosNotificationUtil{

    companion object {
        private fun callGuardian(activity: Activity, phoneNumber: String) {
            val dial = "tel:$phoneNumber"
            activity.startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            //TODO: check if call is actively going on to make call
        }

        private fun textGuardian(activity: Activity, phoneNumber: String) {


        }

        fun notifyGuardian(activity: Activity, guardians: List<Guardian>) {
            for (guardian in guardians) {
                if (guardian.broadcast) {
                    guardian.phoneNumber?.let { phoneNumber ->
                        callGuardian(activity, phoneNumber)
                        textGuardian(activity, phoneNumber)
                    }
                }
            }
        }
    }
}