package com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase

data class HomeUseCase(
    val getUserDetailsUseCase: GetUserDetailsUseCase,
    val getRecentTransactionUseCase: GetRecentTransactionUseCase,
    val getExpenditureUseCase: GetExpenditureUseCase
)