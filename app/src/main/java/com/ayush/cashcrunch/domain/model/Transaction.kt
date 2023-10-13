package com.ayush.cashcrunch.domain.model

import com.ayush.cashcrunch.core.Expense

data class Transaction(
    val userId: String,
    val title: String,
    val amount: String,
    val category: String,
    val type: Expense = Expense.SPEND,
    val date: String,
    val time: Long = 0L,
)
