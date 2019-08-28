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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.wilderpereira.redwood.R
import com.wilderpereira.redwood.domain.RgbHistogram




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
        drawChart(view.findViewById(R.id.redBarChart), histogram!!.getRedHistogram())
        drawChart(view.findViewById(R.id.greenBarChart), histogram!!.getGreenHistogram())
        drawChart(view.findViewById(R.id.blueBarChart), histogram!!.getblueHistogram())
        return view
    }

    private fun drawChart(barChart: BarChart, colorFrequency: Map<Int, Long>) {
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setMaxVisibleValueCount(2048)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)

        val xl = barChart.xAxis
        xl.granularity = 1f
        xl.setCenterAxisLabels(true)

        val leftAxis = barChart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 30f
        barChart.axisRight.isEnabled = false

        //data
        val barWidth = 0.46f

        val minValue = 0
        val maxValue = 255

        val yVals1 = ArrayList<BarEntry>()

        for (x in minValue until maxValue) {
            var barValue = 0L
            if (colorFrequency.containsKey(x)) {
                barValue = colorFrequency[x]!!
            }
            yVals1.add(BarEntry(x.toFloat(), barValue.toFloat()))
        }

        val set1: BarDataSet

        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = yVals1
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "Pixel value")
            set1.color = Color.rgb(104, 241, 175)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            barChart.data = data
        }

        barChart.barData.barWidth = barWidth
        barChart.invalidate()
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
