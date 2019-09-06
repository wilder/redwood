package com.wilderpereira.redwood.domain
import android.graphics.Bitmap
import android.os.Environment
import com.wilderpereira.redwood.BrightnessFilter
import com.wilderpereira.redwood.domain.filters.ImageFilter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ImageManager {

    lateinit var originalImage: Bitmap
    lateinit var currentImage: Bitmap
    lateinit var currentHistogram: RgbHistogram

    private val brightnessFilter = BrightnessFilter()

    fun applyFilter(filter: ImageFilter): Bitmap {
        currentImage = filter.apply(this.currentImage)
        return currentImage
    }

    fun changeBrightness(brightnessValue: Int): Bitmap {
        brightnessFilter.setBrightnessValue(brightnessValue)
        return brightnessFilter.apply(currentImage)
    }

    fun loadImage(image: Bitmap) {
        this.originalImage = image
        this.currentImage = image
        this.currentHistogram = buildHistogram()
    }

    fun buildHistogram(): RgbHistogram {
        return if (this::currentImage.isInitialized) {
            HistogramBuilder(currentImage).build()
        } else {
            //TODO: custom error
            throw Exception("No image selected")
        }
    }

    fun reset() {
        this.currentImage = originalImage
    }

    fun save() {
        if (this::currentImage.isInitialized) {
            saveToInternalStorage(this.currentImage)
        } else {
            //TODO: no image selected error
            throw Exception("No image selected")
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap) {

        var outStream: FileOutputStream?

        // TODO: remove Enviroment dependency, extract to other class
        try {
            val sdCard = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.absolutePath + "/redwood")
            dir.mkdirs()

            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(dir, fileName)

            outStream = FileOutputStream(outFile)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream!!.flush()
            outStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
        }

    }

}

