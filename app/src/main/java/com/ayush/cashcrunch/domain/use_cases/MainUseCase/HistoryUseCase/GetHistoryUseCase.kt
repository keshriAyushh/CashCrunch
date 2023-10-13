package com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase

data class GetHistoryUseCase(
    val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    val getMonthlyExpenditureUseCase: GetMonthlyExpenditureUseCase
)
