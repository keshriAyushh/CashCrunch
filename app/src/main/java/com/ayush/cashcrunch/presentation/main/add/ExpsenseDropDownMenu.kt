package com.ayush.cashcrunch.presentation.main.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.presentation.ui.theme.Green
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack
import com.ayush.cashcrunch.presentation.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDownMenu(
    expense: String,
    onChange: (String) -> Unit
) {

    var isDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isDropDownExpanded,
        onExpandedChange = {
            isDropDownExpanded = it
        },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 10.dp, end = 10.dp)

    ) {
        OutlinedTextField(
            value = expense,
            onValueChange = {

            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isDropDownExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = MatteBlack,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                focusedTextColor = MatteBlack,
                unfocusedTrailingIconColor = Color.Gray,
                focusedTrailingIconColor = MatteBlack
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(1f)
                .clickable {
                    isDropDownExpanded = !isDropDownExpanded
                }
        )

        ExposedDropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = { isDropDownExpanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Expense",
                        color = Red,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Expense")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Income",
                        color = Green,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Income")
                    isDropDownExpanded = false
                }
            )
        }
    }
}