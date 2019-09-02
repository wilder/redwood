package com.wilderpereira.redwood.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wilderpereira.redwood.R
import com.wilderpereira.redwood.data.AndroidImageResolver
import com.wilderpereira.redwood.domain.ImageFilter
import com.wilderpereira.redwood.domain.RgbHistogram
import com.wilderpereira.redwood.helpers.PICK_IMAGE
import com.wilderpereira.redwood.helpers.selectImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private var hasSetImage = false
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = getString(R.string.app_name_bar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_launcher_round)
        presenter =
            ImageProcessingPresenter(this, AndroidImageResolver(contentResolver))
        setupImageView()
        setupSeekBar()
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

    override fun displayHistogram(histogram: RgbHistogram) {
        HistogramFragment.newInstance(histogram)
            .show(supportFragmentManager, "histogramDialog")
    }

    override fun displayHistogramError() {
        Toast.makeText(this, R.string.no_image_provided, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoadingView() {
        progressBar.visibility = View.VISIBLE
        Log.d("Main", "loading")
    }

    override fun hideLoadingView() {
        Log.d("Main", "hiding")
        progressBar.visibility = View.GONE
        Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show()
    }

    override fun displaySelectedImageError() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_histogram -> {
                presenter.buildHistogram()
                true
            }
            R.id.menu_reset -> {
                presenter.removeAllFilters()
                seekBar.progress = 128
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
