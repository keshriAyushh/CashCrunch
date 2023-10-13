package com.ayush.cashcrunch.presentation.main.history

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.presentation.main.components.TransactionItem
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel()
) {

    var showGraphEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    val rotationState: State<Float> =
        animateFloatAsState(targetValue = if (showGraphEnabled) 180f else 0f, label = "rotate_icon")

    LaunchedEffect(key1 = Unit) {
        historyViewModel.getAllTransactions()
        historyViewModel.getMonthlyExpenditureData()
    }

    val allTransactionsState = historyViewModel.allTransactionsState
        .collectAsState()

    val context = LocalContext.current

    val monthlyExpenditureData = historyViewModel.monthlyExpenditureState.collectAsState()
    var monthWiseExpense = emptyList<Expenditure>()
    monthlyExpenditureData.value.let {
        when (it) {
            is Response.Error -> {
                showToast(it.message, context)
            }

            Response.Loading -> {
                ShowProgressIndicator()
            }

            is Response.Success -> {
                for (x in it.data) {
                    monthWiseExpense = it.data
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
            .animateContentSize(
                tween(
                    durationMillis = 300,
                    easing = FastOutLinearInEasing
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.09f)
                    .background(MatteBlack),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "All Transactions",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        modifier = Modifier.weight(3f)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.bars_staggered),
                        contentDescription = "show_graph",
                        tint = Color.White,
                        modifier = Modifier
                            .weight(0.4f)
                            .size(20.dp)
                            .rotate(rotationState.value)
                            .clickable {
                                showGraphEnabled = !showGraphEnabled
                            }
                    )
                }
            }
            if (showGraphEnabled) {
                ChartSection(monthlyExpense = monthWiseExpense)
            }
            Spacer(modifier = Modifier.height(15.dp))
            allTransactionsState.value.let { it ->
                when (it) {
                    is Response.Error -> {
                        showToast(it.message, context)
                    }

                    Response.Loading -> {
                        ShowProgressIndicator()
                    }

                    is Response.Success -> {
                        LazyColumn {
                            items(it.data) {
                                TransactionItem(transaction = it)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                }
            }
        }
    }
}


fun showToast(message: String, context: Context) {
    Toast
        .makeText(context, message, Toast.LENGTH_SHORT)
        .show()
}

@Composable
fun ShowProgressIndicator() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScreen() {
    HistoryScreen()
}