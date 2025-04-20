package com.example.graafi.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun GraphScreen(viewModel: HeartRateViewModel) {
    val context = LocalContext.current
    val data = viewModel.heartRateList.value ?: emptyList()

    AndroidView(factory = {
        val chart = LineChart(context)
        val entries = data.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val dataSet = LineDataSet(entries, "Heart Rate").apply {
            color = ColorTemplate.COLORFUL_COLORS[0]
            setDrawValues(true)
            setDrawCircles(true)
        }

        chart.data = LineData(dataSet)
        chart.invalidate() // Refresh
        chart
    })
}
