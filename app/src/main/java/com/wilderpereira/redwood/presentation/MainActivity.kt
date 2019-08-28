package com.wilderpereira.redwood.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
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
        setupSeekBar()
    }

    private fun setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, value: Int, p2: Boolean) {
                presenter.changeBrightness(value)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun displayImage(image: Bitmap) {
        this.imageView.setImageBitmap(image)
        seekBar.visibility = View.VISIBLE
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

    override fun displayLoadingView() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        progressBar.visibility = View.GONE
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
