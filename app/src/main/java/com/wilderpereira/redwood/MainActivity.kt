package com.wilderpereira.redwood

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.view.View
import com.wilderpereira.redwood.helpers.PICK_IMAGE
import com.wilderpereira.redwood.helpers.selectImage


class MainActivity : AppCompatActivity() {

    private var hasSetImage = false
    private lateinit var imageManager: ImageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE -> displayImageAndFilters(data)
            }
        } else {
            //TODO: display error
        }
    }

    private fun displayImageAndFilters(data: Intent?) {
        val uri = data?.data
        if (uri != null) {
            imageManager = ImageManager(uri, contentResolver)
            effectsRecyclerView.visibility = View.VISIBLE
            this.imageView.setImageBitmap(imageManager.originalImageBitmap)
        } else {
            //TODO: display error message
        }
    }

    private fun setupViews() {
        setupFiltersRecycler()
        setupImageView()
    }

    private fun setupFiltersRecycler() {
        effectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupImageView() {
        imageView.setOnClickListener {
            if (hasSetImage) {
                showOriginalImage()
            } else {
                selectImage(this)
                hasSetImage = true
            }
        }
    }

    private fun showOriginalImage() {
    }

}
