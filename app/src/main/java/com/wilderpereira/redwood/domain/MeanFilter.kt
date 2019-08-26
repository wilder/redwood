package com.wilderpereira.redwood.domain

import android.graphics.Bitmap

class MeanFilter : ImageFilter {

    override fun getName(): String = "Mean"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {

        val neighborsPixels = mutableListOf<Int>()
        neighborsPosition.forEach {
            if (isInBounds(bitmap, line + it.first, column + it.second)) {
                neighborsPixels.add(bitmap.getPixel(line + it.first, column + it.second))
            }
        }

        return neighborsPixels.sum() / neighborsPixels.size

    }

}