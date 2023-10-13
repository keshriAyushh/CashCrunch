package com.ayush.cashcrunch.domain.use_cases.MainUseCase.AddTransactionUseCase

import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.repository.MainRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(
        transaction: Transaction
    ) = mainRepository.addUserTransaction(
        transaction
    )
}
