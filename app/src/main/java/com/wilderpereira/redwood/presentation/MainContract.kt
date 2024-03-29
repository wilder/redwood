package com.wilderpereira.redwood.presentation

import android.graphics.Bitmap
import android.net.Uri
import com.wilderpereira.redwood.domain.RgbHistogram
import com.wilderpereira.redwood.domain.filters.ImageFilter

interface MainContract {

    interface View {
        fun displayLoadingView()
        fun hideLoadingView()
        fun displayImage(image: Bitmap)
        fun displayFilters(filters: List<ImageFilter>)
        fun displaySelectedImageError()
        fun displayHistogram(rgbHistogram: RgbHistogram)
        fun displayImageNotSelectedError()
    }

    interface Presenter {
        fun loadFilters()
        fun handleSelectedImage(uri: Uri?)
        fun applyFilter(filter: ImageFilter)
        fun changeBrightness(value: Int)
        fun buildHistogram()
        fun removeAllFilters()
        fun saveCurrentImage()
    }

}