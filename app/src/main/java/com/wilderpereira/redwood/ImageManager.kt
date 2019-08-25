package com.wilderpereira.redwood

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

class ImageManager(originalImageUri: Uri, contentResolver: ContentResolver) {

    val originalImageBitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, originalImageUri)

    fun applyFilter(filter: ImageFilter): Bitmap = filter.apply(this.originalImageBitmap)


}

