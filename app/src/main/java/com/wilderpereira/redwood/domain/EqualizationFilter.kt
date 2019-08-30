package com.wilderpereira.redwood.domain

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.floor
import kotlin.math.max

class EqualizationFilter(private val histogram: RgbHistogram) : ImageFilter {

    private var equalizedHistograms: List<List<Int>>

    init {
        val cumulative = histogram.computeCumulativeHistogram()
        this.equalizedHistograms = equalizeHistograms(cumulative)
    }

    private fun equalizeHistograms(cumulative: List<List<Long>>): List<List<Int>> {
        return histogram.rgbPixelOccurrences.flatMap { frequencies ->
            cumulative.map { list -> equalizeValue(frequencies, list) }
        }
    }

    override fun getName(): String = "Equalization"

    override fun filter(bitmap: Bitmap, line: Int, column: Int): Int {
        val redEqualizedHistogram = equalizedHistograms[0]
        val greenEqualizedHistogram = equalizedHistograms[1]
        val blueEqualizedHistogram = equalizedHistograms[2]

        val pixel = bitmap.getPixel(line, column)

        val red = redEqualizedHistogram[Color.red(pixel)]
        val green = greenEqualizedHistogram[Color.green(pixel)]
        val blue = blueEqualizedHistogram[Color.blue(pixel)]

        return Color.rgb(red, green, blue)
    }

    fun equalizeValue(histogram: List<Long>, cumulative: List<Long>): List<Int> {
        val i = histogram.sum() / 255
        return cumulative.map { max(0.toDouble(), floor((it/i).toDouble()) - 1).toInt() }
    }

}