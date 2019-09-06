package com.wilderpereira.redwood

import android.graphics.Bitmap
import android.graphics.Color
import com.wilderpereira.redwood.domain.filters.ImageFilter

class BrightnessFilter : ImageFilter {

    private var brightness: Int = 0
    private val defaultBrightness = 126
    private val maxPixelValue = 255
    private val minPixelValue = 0

    override fun getName(): String = "Brightness"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {

        val pixel = bitmap.getPixel(line, column)
        val red = increaseUntilThreshold(Color.red(pixel))
        val green = increaseUntilThreshold(Color.green(pixel))
        val blue = increaseUntilThreshold(Color.blue(pixel))

        return Color.rgb(red, green, blue)

    }

    private fun increaseUntilThreshold(pixel: Int): Int {
        val increasedValue = pixel + brightness
        return when {
            increasedValue > maxPixelValue -> 255
            increasedValue < minPixelValue ->  0
            else -> increasedValue
        }
    }

    fun setBrightnessValue(brightness: Int) {
        this.brightness = normalize(brightness)
    }

    private fun normalize(brightness: Int) = brightness - defaultBrightness

}