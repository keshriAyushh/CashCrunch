package com.ayush.cashcrunch.presentation.main.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.MakeBlocksOf7
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack
import com.ayush.cashcrunch.presentation.ui.theme.Red

@Composable
fun ChartSection(
    modifier: Modifier = Modifier,
    monthlyExpense: List<Expenditure>
) {

    val points = MakeBlocksOf7.makeBlocksOf7(monthlyExpense)

    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(1f)
            .background(MatteBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (points.size < 2) {
                Text(
                    text = "Add transactions for atleast a week to get graphical overview",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                SpendsChart(
                    modifier,
                    points
                )
            }

        }
    }
}

@Composable
fun SpendsChart(
    modifier: Modifier = Modifier,
    points: List<MakeBlocksOf7.ExpenseData>
) {
    val steps = 10

    val pointsData = mutableListOf<Point>()

    for (i in points.indices) {
        pointsData.add(Point(i.toFloat(), points[i].spends.toFloat()))
    }

    Log.d("pointsdata", pointsData.toString())
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .axisLabelColor(Color.White)
        .axisLineColor(Color.White)
        .backgroundColor(MatteBlack)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .axisLabelColor(Color.White)
        .axisLineColor(Color.White)
        .steps(steps)
        .backgroundColor(MatteBlack)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = pointsData[pointsData.size - 1].y.toInt() / steps
            (i * yScale).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Red
                    ),
                    IntersectionPoint(
                        color = Color.White
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MatteBlack
    )

    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}