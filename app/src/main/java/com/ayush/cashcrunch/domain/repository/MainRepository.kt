package com.ayush.cashcrunch.domain.repository

import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Points
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.model.User
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getUserDetails(): Flow<Response<User>>

    fun getRecentTransactions(): Flow<Response<List<Transaction>>>

    fun getAllTransactions(): Flow<Response<List<Transaction>>>

    fun addUserTransaction(transaction: Transaction): Flow<Response<Boolean>>

    fun getExpenditure(): Flow<Response<Expenditure>>

    fun getMonthlyExpenditureData(): Flow<Response<List<Expenditure>>>
}