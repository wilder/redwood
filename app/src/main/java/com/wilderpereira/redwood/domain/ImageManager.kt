package com.wilderpereira.redwood.domain
import android.graphics.Bitmap

class ImageManager() {

    lateinit var currentImage: Bitmap

    fun applyFilter(filter: ImageFilter): Bitmap {
        return filter.apply(this.currentImage)
    }


}

