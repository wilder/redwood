package com.wilderpereira.redwood.domain

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

class RgbHistogram() : Parcelable {

    private val redPixelOccurrenceMap: MutableMap<Int, Long> = mutableMapOf()
    private val greenPixelOccurrenceMap: MutableMap<Int, Long> = mutableMapOf()
    private val bluePixelOccurrenceMap: MutableMap<Int, Long> = mutableMapOf()

    private val colorPixelOccurrenceMap = mapOf(
        Color.RED to redPixelOccurrenceMap,
        Color.GREEN to greenPixelOccurrenceMap,
        Color.BLUE to bluePixelOccurrenceMap
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
        if (!colorMap.containsKey(key)) {
            colorMap[key] = 1
        } else {
            colorMap[key] = colorMap[key]!! + 1
        }
    }

    fun getRedHistogram() = redPixelOccurrenceMap.toMap()

    fun getGreenHistogram() = greenPixelOccurrenceMap.toMap()

    fun getblueHistogram() = bluePixelOccurrenceMap.toMap()

    override fun toString(): String {
        return "'red' : ${getRedHistogram()},\n'green': ${getGreenHistogram()},\n'blue': ${getblueHistogram()}"
    }

    constructor(source: Parcel) : this() {
        source.readMap(redPixelOccurrenceMap as Map<Int, Long>, Long::class.java.classLoader)
        source.readMap(greenPixelOccurrenceMap as Map<Int, Long>, Long::class.java.classLoader)
        source.readMap(bluePixelOccurrenceMap as Map<Int, Long>, Long::class.java.classLoader)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeMap(redPixelOccurrenceMap as Map<Int, Long>)
        writeMap(greenPixelOccurrenceMap as Map<Int, Long>)
        writeMap(bluePixelOccurrenceMap as Map<Int, Long>)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RgbHistogram> = object : Parcelable.Creator<RgbHistogram> {
            override fun createFromParcel(source: Parcel): RgbHistogram = RgbHistogram(source)
            override fun newArray(size: Int): Array<RgbHistogram?> = arrayOfNulls(size)
        }
    }
}
