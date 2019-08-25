package com.wilderpereira.redwood.helpers

import android.app.Activity
import android.content.Intent

const val PICK_IMAGE = 1

fun selectImage(activity: Activity) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
}