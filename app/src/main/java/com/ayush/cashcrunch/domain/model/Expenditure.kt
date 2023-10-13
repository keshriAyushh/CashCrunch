package com.ayush.cashcrunch.domain.model

data class Expenditure(
    val userId: String,
    val totalSpends: String,
    val totalIncome: String,
    val netBalance: String
)
