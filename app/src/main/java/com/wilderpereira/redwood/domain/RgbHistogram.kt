package com.wilderpereira.redwood.domain

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

class RgbHistogram() : Parcelable {

    private val redPixelOccurrence: MutableList<Long> = MutableList(256) {0L}
    private val greenPixelOccurrence: MutableList<Long> = MutableList(256) {0L}
    private val bluePixelOccurrence: MutableList<Long> = MutableList(256) {0L}

    private val colorPixelOccurrenceMap = mapOf(
        Color.RED to redPixelOccurrence,
        Color.GREEN to greenPixelOccurrence,
        Color.BLUE to bluePixelOccurrence
    )

    fun registerOccurrence(pixel: Int) {
        val redPixel = Color.red(pixel)
        val greenPixel = Color.green(pixel)
        val bluePixel = Color.blue(pixel)

        registerRedOccurrence(redPixel)
        registerGreenOccurrence(greenPixel)
        registerBlueOccurrence(bluePixel)
    }

    private fun registerRedOccurrence(value: Int) {
        registerOccurrence(value, Color.RED)
    }

    private fun registerGreenOccurrence(value: Int) {
        registerOccurrence(value, Color.GREEN)
    }

    private fun registerBlueOccurrence(value: Int) {
        registerOccurrence(value, Color.BLUE)
    }

    private fun registerOccurrence(key: Int, color: Int) {
        val colorMap = colorPixelOccurrenceMap[color] ?: error("Invalid color map")
        if (key in 0..255) {
            colorMap[key] = colorMap[key] + 1
        }
    }

    fun getRedHistogram() = redPixelOccurrence.toList()

    fun getGreenHistogram() = greenPixelOccurrence.toList()

    fun getblueHistogram() = bluePixelOccurrence.toList()

    override fun toString(): String {
        return "'red' : ${getRedHistogram()},\n'green': ${getGreenHistogram()},\n'blue': ${getblueHistogram()}"
    }

    constructor(source: Parcel) : this() {
        source.readList(redPixelOccurrence as List<Long>, Long::class.java.classLoader)
        source.readList(greenPixelOccurrence as List<Long>, Long::class.java.classLoader)
        source.readList(bluePixelOccurrence as List<Long>, Long::class.java.classLoader)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(redPixelOccurrence as List<Long>)
        writeList(greenPixelOccurrence as List<Long>)
        writeList(bluePixelOccurrence as List<Long>)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RgbHistogram> = object : Parcelable.Creator<RgbHistogram> {
            override fun createFromParcel(source: Parcel): RgbHistogram = RgbHistogram(source)
            override fun newArray(size: Int): Array<RgbHistogram?> = arrayOfNulls(size)
        }
    }
}
