package com.wilderpereira.redwood.helpers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 4

fun requestPermission(activity: Activity, permission: String, function: () -> Unit) {
    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
    } else {
        function()
    }
}