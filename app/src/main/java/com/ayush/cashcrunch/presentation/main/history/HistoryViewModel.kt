package com.ayush.cashcrunch.presentation.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyUseCase: GetHistoryUseCase
) : ViewModel() {

    private var _allTransactionsState = MutableStateFlow<Response<List<Transaction>>>(
        Response.Success(
            emptyList()
        )
    )
    val allTransactionsState: StateFlow<Response<List<Transaction>>> = _allTransactionsState

    private var _monthlyExpenditureState = MutableStateFlow<Response<List<Expenditure>>>(
        Response.Success(
            emptyList()
        )
    )

    val monthlyExpenditureState = _monthlyExpenditureState

    init {
        getAllTransactions()
    }


    fun getAllTransactions() {
        viewModelScope.launch {
            historyUseCase.getAllTransactionsUseCase().collect {
                _allTransactionsState.value = it
            }
        }
    }

    fun getMonthlyExpenditureData() {
        viewModelScope.launch {
            historyUseCase.getMonthlyExpenditureUseCase().collect {
                _monthlyExpenditureState.value = it
            }
        }
    }
}