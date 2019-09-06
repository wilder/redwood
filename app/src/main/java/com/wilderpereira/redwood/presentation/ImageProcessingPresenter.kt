package com.wilderpereira.redwood.presentation

import android.net.Uri
import com.wilderpereira.redwood.data.ImageResolver
import com.wilderpereira.redwood.domain.ImageManager
import com.wilderpereira.redwood.domain.filters.EqualizationFilter
import com.wilderpereira.redwood.domain.filters.ImageFilter
import com.wilderpereira.redwood.domain.filters.MeanFilter
import com.wilderpereira.redwood.domain.filters.MedianFilter


class ImageProcessingPresenter(private val view: MainContract.View, private val imageResolver: ImageResolver) :
    MainContract.Presenter {

    private val imageManager = ImageManager()

    override fun loadFilters() {
        val filters = listOf(
            MeanFilter(),
            MedianFilter(),
            EqualizationFilter(imageManager.currentHistogram)
        )
        view.displayFilters(filters)
    }

    override fun handleSelectedImage(uri: Uri?) {
        if (uri != null) {
            imageManager.loadImage(imageResolver.loadImageFromUri(uri))
            view.displayImage(imageManager.currentImage)
        } else {
            view.displaySelectedImageError()
        }
    }

    override fun applyFilter(filter: ImageFilter) {
        view.displayLoadingView()
        view.displayImage(imageManager.applyFilter(filter))
        view.hideLoadingView()
    }

    override fun changeBrightness(value: Int) {
        view.displayImage(imageManager.changeBrightness(value))
    }

    override fun buildHistogram() {
        try {
            view.displayHistogram(imageManager.currentHistogram)
        } catch (exception: Exception) {
            view.displayImageNotSelectedError()
        }
    }

    override fun removeAllFilters() {
        imageManager.reset()
        view.displayImage(imageManager.originalImage)
    }

    override fun saveCurrentImage() {
        view.displayLoadingView()
        try {
            imageManager.save()
        } catch (exception: Exception) {
            view.displayImageNotSelectedError()
        }
        view.hideLoadingView()
    }

}