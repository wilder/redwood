package com.wilderpereira.redwood.domain

import android.graphics.Bitmap
import android.graphics.Color

class MedianFilter : ImageFilter {

    override fun getName(): String = "Median"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {

        val neighborsPixels = mutableListOf<Int>()
        neighborsPosition.forEach {
            if (isInBounds(bitmap, line + it.first, column + it.second)) {
                neighborsPixels.add(bitmap.getPixel(line + it.first, column + it.second))
            }
        }

        val red = mutableListOf<Int>()
        val green = mutableListOf<Int>()
        val blue = mutableListOf<Int>()

        neighborsPixels.forEach {
            red.add(Color.red(it))
            green.add(Color.green(it))
            blue.add(Color.blue(it))
        }

        red.sort()
        green.sort()
        blue.sort()

        val middle = neighborsPixels.size / 2
        return if (middle % 2 == 1) {
            Color.rgb(red[middle], green[middle], blue[middle])
        } else {
            Color.rgb(
            (red[middle - 1] + red[middle]) / 2,
            (green[middle - 1] + green[middle]) / 2,
            (blue[middle - 1] + blue[middle]) / 2
            )
        }

    }

}