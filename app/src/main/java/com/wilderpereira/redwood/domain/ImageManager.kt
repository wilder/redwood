package com.wilderpereira.redwood.domain
import android.graphics.Bitmap
import com.wilderpereira.redwood.BrightnessFilter

class ImageManager {

    lateinit var currentImage: Bitmap

    private val brightnessFilter = BrightnessFilter()

    fun applyFilter(filter: ImageFilter): Bitmap {
        return filter.apply(this.currentImage)
    }

    fun changeBrightness(brightnessValue: Int): Bitmap {
        brightnessFilter.setBrightnessValue(brightnessValue)
        return brightnessFilter.apply(currentImage)
    }


}

