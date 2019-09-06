package com.wilderpereira.redwood.domain.filters

import android.graphics.Bitmap
import android.graphics.Color

class MeanFilter : ImageFilter {

    override fun getName(): String = "Mean"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {

        val neighborsPixels = mutableListOf<Int>()
        neighborsPosition.forEach {
            if (isInBounds(bitmap, line + it.first, column + it.second)) {
                neighborsPixels.add(bitmap.getPixel(line + it.first, column + it.second))
            }
        }

        var red = 0
        var green = 0
        var blue = 0

        neighborsPixels.forEach {
            red += Color.red(it)
            green += Color.green(it)
            blue += Color.blue(it)
        }

        red /= neighborsPixels.size
        green /= neighborsPixels.size
        blue /= neighborsPixels.size

        return Color.rgb(red, green, blue)

    }

}