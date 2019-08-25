package com.wilderpereira.redwood

import android.graphics.Bitmap

interface ImageFilter {

    fun apply(bitmap: Bitmap): Bitmap

}
