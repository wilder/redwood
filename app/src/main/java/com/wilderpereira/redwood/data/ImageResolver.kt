package com.wilderpereira.redwood.data

import android.graphics.Bitmap
import android.net.Uri

interface ImageResolver {

    fun loadImageFromUri(uri: Uri): Bitmap

}