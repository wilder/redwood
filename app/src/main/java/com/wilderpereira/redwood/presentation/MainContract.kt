package com.wilderpereira.redwood.presentation

import android.graphics.Bitmap
import android.net.Uri
import com.wilderpereira.redwood.domain.ImageFilter

interface MainContract {

    interface View {
        fun displayLoadingView()
        fun hideLoadingView()
        fun displayImage(image: Bitmap)
        fun displayFilters(filters: List<ImageFilter>)
        fun displaySelectedImageError()
    }

    interface Presenter {
        fun loadFilters()
        fun handleSelectedImage(uri: Uri?)
        fun applyFilter(filter: ImageFilter)
    }

}