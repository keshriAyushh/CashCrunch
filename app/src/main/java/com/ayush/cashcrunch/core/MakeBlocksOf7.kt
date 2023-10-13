package com.ayush.cashcrunch.core

import com.ayush.cashcrunch.domain.model.Expenditure

object MakeBlocksOf7 {

    data class ExpenseData(
        val income: String,
        val spends: String
    )

    fun makeBlocksOf7(expenses: List<Expenditure>): List<ExpenseData> {

        val list = mutableListOf<ExpenseData>()
        var totalIncomeForAWeek = 0
        var totalSpendsForAWeek = 0
        var count = 1

        for(x in expenses) {

            totalIncomeForAWeek += x.totalIncome.toInt()
            totalSpendsForAWeek += x.totalSpends.toInt()

            if(count%7 == 0) {
                list.add(
                    ExpenseData(
                        income = totalIncomeForAWeek.toString(),
                        spends = totalSpendsForAWeek.toString()
                    )
                )
                totalIncomeForAWeek = 0
                totalSpendsForAWeek = 0
            } else if(expenses.size < 7 && count == expenses.size) {
                list.add(
                    ExpenseData(
                        income = totalIncomeForAWeek.toString(),
                        spends = totalSpendsForAWeek.toString()
                    )
                )
                totalIncomeForAWeek = 0
                totalSpendsForAWeek = 0
            }
            count++
        }
        return list
    }
}