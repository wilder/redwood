package com.wilderpereira.redwood.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wilderpereira.redwood.R
import com.wilderpereira.redwood.data.AndroidImageResolver
import com.wilderpereira.redwood.domain.ImageFilter
import com.wilderpereira.redwood.helpers.PICK_IMAGE
import com.wilderpereira.redwood.helpers.selectImage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {

    private var hasSetImage = false
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter =
            ImageProcessingPresenter(this, AndroidImageResolver(contentResolver))
        setupImageView()
    }

    override fun displayImage(image: Bitmap) {
        this.imageView.setImageBitmap(image)
        this.presenter.loadFilters()
    }

    override fun displayFilters(filters: List<ImageFilter>) {
        effectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = FilterRecyclerViewAdapter(filters) { filter -> presenter.applyFilter(filter)}
        }
        effectsRecyclerView.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE -> presenter.handleSelectedImage(data?.data)
            }
        } else {
            displaySelectedImageError()
        }
    }

    override fun displaySelectedImageError() {
    }

    private fun setupImageView() {
        imageView.setOnClickListener {
            if (hasSetImage) {
                //TODO: showOriginalImage()
            } else {
                selectImage(this)
                hasSetImage = true
            }
        }
    }

}
