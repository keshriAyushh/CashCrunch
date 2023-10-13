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
import androidx.compose.runtime.MutableState
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
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDownMenu(
    category: String,
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
            value = category,
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
                        text = "Food",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Food")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Travel",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Travel")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Leisure",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Leisure")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Work",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Work")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Salary",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Salary")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Miscellaneous",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Miscellaneous")
                    isDropDownExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Others",
                        color = MatteBlack,
                        fontFamily = FontFamily(Font(R.font.font2)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                onClick = {
                    onChange("Others")
                    isDropDownExpanded = false
                }
            )
        }
    }
}