package com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase

import com.ayush.cashcrunch.domain.repository.MainRepository
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke() = mainRepository.getAllTransactions()

}