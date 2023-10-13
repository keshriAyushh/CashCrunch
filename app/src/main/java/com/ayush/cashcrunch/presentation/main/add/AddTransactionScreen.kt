package com.ayush.cashcrunch.presentation.main.add

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Expense
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.core.DateUtil
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.presentation.auth.AuthViewModel
import com.ayush.cashcrunch.presentation.main.history.ShowProgressIndicator
import com.ayush.cashcrunch.presentation.main.history.showToast
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    addTransactionViewModel: AddTransactionViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var currentUserId: String = ""

    LaunchedEffect(key1 = Unit) {
        if(authViewModel.isUserAuthenticated) {
            currentUserId = authViewModel.getCurrentUserId
        }
    }

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var title by rememberSaveable {
        mutableStateOf("")
    }

    var calendarState = rememberUseCaseState()

    var selectedDate by rememberSaveable {
        mutableStateOf("")
    }

    var category by rememberSaveable {
        mutableStateOf("Select Category")
    }

    var expense by rememberSaveable {
        mutableStateOf("Select Transaction type")
    }

    val addTransactionState = addTransactionViewModel.addTransactionState.collectAsState()
    val context = LocalContext.current

    addTransactionState.value.let {
        when(it) {
            is Response.Error -> {
                showToast(it.message, context)
            }
            Response.Loading -> {
                ShowProgressIndicator()
            }
            is Response.Success -> {
                if(it.data) {
                    showToast("Added successfully!", context)
                    expense = "Select transaction type"
                    category = "Select Category"
                    amount = ""
                    selectedDate = "[SELECT DATE]"
                    title = ""
                }
            }
        }
    }





    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.09f)
                .background(MatteBlack)
                .padding(10.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = "Add Transaction",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.font2)),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Fill all the details below",
            color = MatteBlack,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.font2)),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(1f)
        )

        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(
                width = 0.1.dp,
                Color.Black
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                focusedElevation = 8.dp,
                pressedElevation = 10.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    placeholder = {
                        Text(
                            text = "Title",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Gray,
                        focusedTrailingIconColor = MatteBlack,
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = MatteBlack,
                        unfocusedBorderColor = Color.Gray,
                        unfocusedLeadingIconColor = Color.Gray,
                        focusedLeadingIconColor = MatteBlack
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    placeholder = {
                        Text(
                            text = "Amount",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.rupee),
                            contentDescription = "rupee_icon"

                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Gray,
                        focusedTrailingIconColor = MatteBlack,
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = MatteBlack,
                        unfocusedBorderColor = Color.Gray,
                        unfocusedLeadingIconColor = Color.Gray,
                        focusedLeadingIconColor = MatteBlack
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))

                CategoryDropDownMenu(category) {
                    category = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                ExpenseDropDownMenu(expense) {
                    expense = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CalendarDialog(
                    state = calendarState,
                    config = CalendarConfig(
                        monthSelection = true,
                        yearSelection = true,
                        style = CalendarStyle.MONTH
                    ),
                    selection = CalendarSelection.Date { date ->
                        selectedDate = date.toString()
                        selectedDate = DateUtil.formatDate(selectedDate)
                        Log.d("SelectedDate", "$date")
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    OutlinedButton(
                        onClick = {
                            calendarState.show()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .weight(1f),
                    ) {
                        Text(
                            text = "Pick Date",
                            fontFamily = FontFamily(Font(R.font.font2)),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = if (selectedDate.isNotEmpty()) selectedDate else "[SELECT DATE]",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .weight(2f),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        if (
                            title.isNotEmpty() &&
                            amount.isNotEmpty() &&
                            category.isNotEmpty() &&
                            expense.isNotEmpty() &&
                            selectedDate.isNotEmpty()
                        ) {
                            addTransactionViewModel.addTransaction(
                                Transaction(
                                    userId = currentUserId,
                                    title = title,
                                    amount = amount,
                                    category = category,
                                    type = if(expense == "Income") Expense.INCOME else Expense.SPEND,
                                    date = selectedDate,
                                    time = System.currentTimeMillis().toString().toLong()
                                )
                            )

                        } else {
                            showToast("Please fill in all the details", context)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MatteBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Add Transaction",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}


