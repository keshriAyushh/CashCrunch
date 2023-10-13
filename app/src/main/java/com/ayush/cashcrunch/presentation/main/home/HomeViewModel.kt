package com.ayush.cashcrunch.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.model.User
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _userDetailsState =
        MutableStateFlow<Response<User>>(Response.Success(User("", "", "", "")))
    val userDetailsState: StateFlow<Response<User>> = _userDetailsState

    private val _expenditureState =
        MutableStateFlow<Response<Expenditure>>(
            Response.Success(Expenditure("", "", "", ""))
        )

    val expenditureState = _expenditureState

    val _recentTransactionsState = MutableStateFlow<Response<List<Transaction>>>(
        Response.Success(
            emptyList()
        )
    )
    val recentTransactionsState: StateFlow<Response<List<Transaction>>> =
        _recentTransactionsState

    fun getUserDetails() {
        viewModelScope.launch {
            homeUseCase.getUserDetailsUseCase().collect {
                _userDetailsState.value = it
            }
        }
    }

    fun getRecentTransactions() {
        viewModelScope.launch {
            homeUseCase.getRecentTransactionUseCase().collect {
                _recentTransactionsState.value = it
            }
        }
    }

    fun getExpenditure() {
        viewModelScope.launch {
            homeUseCase.getExpenditureUseCase().collect {
                _expenditureState.value = it
            }
        }
    }
}