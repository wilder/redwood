package com.wilderpereira.redwood.presentation

import android.net.Uri
import com.wilderpereira.redwood.domain.ImageFilter
import com.wilderpereira.redwood.domain.ImageManager
import com.wilderpereira.redwood.data.ImageResolver

class ImageProcessingPresenter(val view: MainContract.View, val imageResolver: ImageResolver) :
    MainContract.Presenter {

    val imageManager = ImageManager()

    override fun loadFilters() {

    }

    override fun handleSelectedImage(uri: Uri?) {
        if (uri != null) {
            imageManager.currentImage = imageResolver.loadImageFromUri(uri)
            view.displayImage(imageManager.currentImage)
        } else {
            view.displaySelectedImageError()
        }
    }

    override fun applyFilter(filter: ImageFilter) {
        view.displayImage(imageManager.applyFilter(filter))
    }

}