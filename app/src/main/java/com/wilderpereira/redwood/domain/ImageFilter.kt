package com.wilderpereira.redwood.domain

import android.graphics.Bitmap
import kotlinx.coroutines.*

interface ImageFilter {

    val neighborsPosition: List<Pair<Int, Int>>
        get() = listOf(
            Pair(-1, -1), Pair(-1, 1), Pair(-1, 0), Pair(0, -1),
            Pair(0, 1), Pair(1, -1), Pair(1, 1), Pair(1, 0)
        )

    fun apply(originalBitmap: Bitmap): Bitmap {
        val newBitmap = originalBitmap.copy(originalBitmap.config, true)

        for (line in 0 until newBitmap.width) {
            for (column in 0 until newBitmap.height) {
                val newPixelValue = filter(originalBitmap, line, column)
                newBitmap.setPixel(line, column, newPixelValue)
            }
        }

        return newBitmap
    }

    fun isInBounds(bitmap: Bitmap, line: Int, column: Int) =
        line >= 0 && column >= 0 && line < bitmap.width && column < bitmap.height

    fun filter(bitmap: Bitmap, line: Int, column: Int): Int


    fun getName(): String

}
