package com.ayush.cashcrunch.presentation.main.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Constants.RUPEE
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.model.User
import com.ayush.cashcrunch.presentation.auth.AuthViewModel
import com.ayush.cashcrunch.presentation.main.components.TransactionItem
import com.ayush.cashcrunch.presentation.main.history.ShowProgressIndicator
import com.ayush.cashcrunch.presentation.main.history.showToast
import com.ayush.cashcrunch.presentation.ui.theme.Green
import com.ayush.cashcrunch.presentation.ui.theme.LightGreen
import com.ayush.cashcrunch.presentation.ui.theme.LightRed
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack
import com.ayush.cashcrunch.presentation.ui.theme.Red

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUserDetails()
        homeViewModel.getRecentTransactions()
        homeViewModel.getExpenditure()
    }


    val userDetailsState: State<Response<User>> = homeViewModel.userDetailsState.collectAsState()
    val recentTransactionsState: State<Response<List<Transaction>>> =
        homeViewModel.recentTransactionsState.collectAsState()

    var balance by rememberSaveable {
        mutableStateOf("")
    }
    var expense by rememberSaveable {
        mutableStateOf("")
    }
    var income by rememberSaveable {
        mutableStateOf("")
    }

    val expenditureState = homeViewModel.expenditureState.collectAsState()

    expenditureState.value.let {
        when (it) {
            is Response.Error -> {
                showToast(it.message, context)
            }
            Response.Loading -> {
                ShowProgressIndicator()
            }
            is Response.Success -> {
                balance = it.data.netBalance
                expense = it.data.totalSpends
                income = it.data.totalIncome

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Section1(userDetailsState, context, balance = balance, expense = expense, income = income)
                    Section2(recentTransactionsState, context)

                    Spacer(modifier = Modifier.height(200.dp))

                }
            }
        }
    }
}

@Composable
fun Section1(
    userDetailsState: State<Response<User>>, context: Context,
    balance: String, income: String, expense: String
) {

    userDetailsState.value.let {
        when (it) {
            is Response.Error -> {
                Toast
                    .makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }

            Response.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is Response.Success -> {

                val user = it.data

                var expandedState by remember {
                    mutableStateOf(false)
                }

                val rotation = animateFloatAsState(
                    targetValue = if (expandedState) 180f else 0f,
                    label = "rotation"
                )

                Box(
                    modifier = Modifier
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 50,
                                easing = LinearOutSlowInEasing,

                                )
                        )
                        .fillMaxWidth(1f)
                        .height(if (expandedState) 350.dp else 180.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        )
                        .background(MatteBlack)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(1f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "pfp",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        shape = CircleShape,
                                        color = MatteBlack
                                    )
                                    .border(
                                        width = 0.5.dp,
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                    .padding(10.dp)
                                    .weight(1.3f)
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.weight(3f)
                            ) {
                                Text(
                                    text = "Hello ${user.name}",
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.font2)),
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "Welcome",
                                    color = Color.Gray,
                                    fontFamily = FontFamily(Font(R.font.font2)),
                                    fontSize = 14.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(200.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.bars_staggered),
                                contentDescription = "more_options",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(rotation.value)
                                    .clickable {
                                        expandedState = !expandedState
                                    }
                                    .weight(0.8f)
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(
                            text = "Balance",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font2)),
                            modifier = Modifier.padding(start = 10.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${RUPEE}${if(balance == "") 0 else balance}",
                            color = Color.White,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(start = 10.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.font2))
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        if (expandedState) {
                            DetailSection(expense, income)
                        }

                    }
                }

            }
        }
    }


}

@Composable
fun Section2(recentTransactionsState: State<Response<List<Transaction>>>, context: Context) {

    recentTransactionsState.value.let {
        when (it) {
            is Response.Error -> {
                Toast
                    .makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }

            Response.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is Response.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .padding(start = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "Recent Transactions",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.font2)),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        LazyColumn {
                            items(it.data) {
                                TransactionItem(it)
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DetailSection(expense: String, income: String) {
    Row(
        modifier = Modifier.fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //Box1
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(start = 10.dp, end = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowUpward,
                    contentDescription = "icon_income",
                    tint = Green,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 40.dp, bottom = 40.dp, end = 5.dp)
                        .size(40.dp)
                        .background(
                            shape = CircleShape,
                            color = LightGreen
                        )
                        .border(
                            width = 0.5.dp,
                            color = LightGreen,
                            shape = CircleShape
                        )
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Text(
                        text = "Income",
                        color = Green,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font2))
                    )
                    Text(
                        text = "${RUPEE}${if(income=="")0 else income}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.font2))
                    )
                }
            }
        }
        //Box2
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(start = 5.dp, end = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowDownward,
                    contentDescription = "icon_expense",
                    tint = Red,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 40.dp, bottom = 40.dp, end = 5.dp)
                        .size(40.dp)
                        .background(
                            shape = CircleShape,
                            color = LightRed
                        )
                        .border(
                            width = 0.5.dp,
                            color = LightRed,
                            shape = CircleShape
                        )
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Text(
                        text = "Expense",
                        color = Red,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font2))
                    )
                    Text(
                        text = "${RUPEE}${if(expense=="")0 else expense}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.font2))
                    )
                }

            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    HomeScreen()
}

