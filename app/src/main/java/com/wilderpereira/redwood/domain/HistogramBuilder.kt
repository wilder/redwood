package com.wilderpereira.redwood.domain

import android.graphics.Bitmap

class HistogramBuilder(val image: Bitmap) {

    fun build(): RgbHistogram {
        val histogram = RgbHistogram()
        for (line in 0 until image.width) {
            for (column in 0 until image.height) {
                histogram.registerOccurrence(image.getPixel(line, column))
            }
        }
        return histogram
    }

}