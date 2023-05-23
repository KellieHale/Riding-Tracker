package com.riding.tracker

import android.Manifest.permission.*
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.permissionx.guolindev.PermissionX

class NotificationUtil {

    //PermissionX is an extension library that handles permission requests
    // and any background work required for permissions.
    //I have one set for each call for permissions; Contacts, Location, and Phone/Text.

    val onContactPermissionsUpdated = MutableLiveData<Boolean>()
    val onLocationPermissionsUpdated = MutableLiveData<Boolean>()
    val onSosPermissionsUpdated = MutableLiveData<Boolean>()

    private val contactPermissions = READ_CONTACTS
    private val locationPermissions = listOf(ACCESS_FINE_LOCATION,
        ACCESS_NETWORK_STATE,
        ACCESS_COARSE_LOCATION)
    private val sosPermissions = listOf(READ_PHONE_STATE,
        READ_CONTACTS,
        CALL_PHONE,
        SEND_SMS)

    fun areContactPermissionsGranted(context: Context): Boolean {
        return PermissionX.isGranted(context, READ_CONTACTS)
    }

    fun requestContactPermissions(fragment: Fragment) {
        PermissionX.init(fragment)
            .permissions(contactPermissions)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Unable to save locally stored contacts without these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "Please enable the Read Contacts permissions from Settings",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
               onContactPermissionsUpdated.value = allGranted
            }
    }

    fun areLocationPermissionsGranted(context: Context): Boolean {
        return PermissionX.isGranted(context, ACCESS_FINE_LOCATION)
                && PermissionX.isGranted(context, ACCESS_COARSE_LOCATION)
                && PermissionX.isGranted(context, ACCESS_NETWORK_STATE)
    }

    fun requestLocationPermissions(fragment: Fragment) {
        PermissionX.init(fragment)
            .permissions(locationPermissions)
            .explainReasonBeforeRequest()
            .onExplainRequestReason {scope, deniedList ->
                scope.showRequestReasonDialog(deniedList,
                    "Core fundamental tasks are based on these permissions",
                    "Ok",
                    "Cancel")
            }
            .onForwardToSettings {scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList,
                    "Please enable Location Services from Settings",
                    "Ok",
                    "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    onLocationPermissionsUpdated.value = allGranted
            }
        }
    }

    fun areSosPermissionsGranted(context: Context): Boolean {
        return PermissionX.isGranted(context, READ_PHONE_STATE)
                && PermissionX.isGranted(context, READ_CONTACTS)
                && PermissionX.isGranted(context, SEND_SMS)
                && PermissionX.isGranted(context, CALL_PHONE)
    }
    fun requestSosPermissions(fragment: Fragment) {
        PermissionX.init(fragment)
            .permissions(sosPermissions)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "These permissions are required for Emergency Phone Call/Text Message function", "Ok", "Cancel")
            }
            .onForwardToSettings {scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList,
                    "Please enable Phone Call and SMS Services from Settings",
                    "Ok",
                    "Cancel")
            }
            .request{allGranted, grantedList, deniedList ->
                if (allGranted) {
                    onSosPermissionsUpdated.value = allGranted
                }
            }

    }
}