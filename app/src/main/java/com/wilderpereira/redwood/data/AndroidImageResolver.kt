package com.wilderpereira.redwood.data

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

class AndroidImageResolver(private val contentResolver: ContentResolver) : ImageResolver {

    override fun loadImageFromUri(uri: Uri): Bitmap =
        MediaStore.Images.Media.getBitmap(contentResolver, uri)

}