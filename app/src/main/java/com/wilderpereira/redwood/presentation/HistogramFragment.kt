package com.wilderpereira.redwood.presentation

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.wilderpereira.redwood.domain.RgbHistogram
import com.wilderpereira.redwood.R

private const val histogramParam = "histogramParam"

class HistogramFragment : DialogFragment() {
    private var histogram: RgbHistogram? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            histogram = it.getParcelable(histogramParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_histogram, container, false)
        drawChart(view.findViewById(R.id.redBarChart), histogram!!.getRedHistogram(), "Red")
        drawChart(view.findViewById(R.id.greenBarChart), histogram!!.getGreenHistogram(), "Green")
        drawChart(view.findViewById(R.id.blueBarChart), histogram!!.getblueHistogram(), "Blue")
        return view
    }

    private fun drawChart(barChart: BarChart, colorFrequency: List<Long>, color: String) {
        val barEntry = mutableListOf<BarEntry>()
        val barEntryLabels = ArrayList<String>()

        for (x in 0 until 256) {
            val barValue = colorFrequency[x].toFloat()
            barEntry.add(BarEntry(x.toFloat(), barValue))
            barEntryLabels.add("$x")
        }

        val barDataSet = BarDataSet(barEntry, "$color Pixels")
        val barData = BarData(barDataSet)
        barData.calcMinMaxY(0.toFloat(), 255.toFloat())

        barDataSet.color = when(color.toLowerCase()) {
            "red" -> Color.RED
            "green" -> Color.GREEN
            "blue" -> Color.BLUE
            else -> Color.BLACK
        }

        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.data = barData
        barChart.description.isEnabled = false
        barChart.animateY(3000)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    companion object {
        @JvmStatic
        fun newInstance(histogram: RgbHistogram) =
            HistogramFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(histogramParam, histogram)
                }
            }
    }
}
