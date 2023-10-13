package com.ayush.cashcrunch.presentation.main.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.AddTransactionUseCase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
):ViewModel() {

    private val _addTransactionsState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val addTransactionState: StateFlow<Response<Boolean>> = _addTransactionsState

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            addTransactionUseCase(transaction)
                .collect {
                    _addTransactionsState.value = it
                }
        }
    }
}