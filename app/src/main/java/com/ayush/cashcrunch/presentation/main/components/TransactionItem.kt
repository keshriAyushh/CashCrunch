package com.ayush.cashcrunch.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Constants.RUPEE
import com.ayush.cashcrunch.core.Expense
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.presentation.ui.theme.Green
import com.ayush.cashcrunch.presentation.ui.theme.LightGreen
import com.ayush.cashcrunch.presentation.ui.theme.LightRed
import com.ayush.cashcrunch.presentation.ui.theme.Red

@Composable
fun TransactionItem(transaction: Transaction) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = if (transaction.type == Expense.INCOME) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                contentDescription = "expense_symbol",
                tint = if (transaction.type == Expense.INCOME) Green else Red,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(0.6f)
                    .size(40.dp)
                    .background(
                        shape = CircleShape,
                        color = if (transaction.type == Expense.INCOME) LightGreen else LightRed
                    )
                    .border(
                        width = 0.5.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .padding(10.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = transaction.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.font2))
                )
                Text(
                    text = transaction.category,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${RUPEE}${transaction.amount}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontSize = 16.sp
                )
                Text(
                    text = transaction.date,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.font2)),
                    fontSize = 14.sp
                )
            }


        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewItem() {
    TransactionItem(
        Transaction(
            title = "Pizza",
            category = "Food",
            type = Expense.SPEND,
            date = "17/9/23",
            userId = "sbhewbfewnfewhifb",
            amount = "700"
        )
    )
}
