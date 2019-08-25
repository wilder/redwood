package com.wilderpereira.redwood.domain

import android.graphics.Bitmap

interface ImageFilter {

    fun apply(bitmap: Bitmap): Bitmap

}
