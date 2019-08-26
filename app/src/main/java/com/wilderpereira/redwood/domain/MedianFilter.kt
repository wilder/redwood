package com.wilderpereira.redwood.domain

import android.graphics.Bitmap

class MedianFilter : ImageFilter {

    override fun getName(): String = "Median"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {

        val neighborsPixels = mutableListOf<Int>()
        neighborsPosition.forEach {
            if (isInBounds(bitmap, line + it.first, column + it.second)) {
                neighborsPixels.add(bitmap.getPixel(line + it.first, column + it.second))
            }
        }

        neighborsPixels.sort()
        val middle = neighborsPixels.size / 2
        return if (middle % 2 == 1) {
            neighborsPixels[middle]
        } else {
            (neighborsPixels[middle - 1] + neighborsPixels[middle]) / 2
        }

    }

}