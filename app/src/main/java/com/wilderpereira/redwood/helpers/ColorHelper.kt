package com.wilderpereira.redwood.helpers

import android.graphics.Color

fun getColorFromColorName(color: String): Int {
    return when (color.toLowerCase()) {
        "red" -> Color.RED
        "green" -> Color.GREEN
        "blue" -> Color.BLUE
        else -> Color.BLACK
    }
}