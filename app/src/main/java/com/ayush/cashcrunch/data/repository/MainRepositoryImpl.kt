package com.ayush.cashcrunch.data.repository

import com.ayush.cashcrunch.core.Constants.EXPENDITURE_COLLECTION
import com.ayush.cashcrunch.core.Constants.TRANSACTION_COLLECTIONS
import com.ayush.cashcrunch.core.Constants.USERS_COLLECTION
import com.ayush.cashcrunch.core.Expense
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.Expenditure
import com.ayush.cashcrunch.domain.model.Points
import com.ayush.cashcrunch.domain.model.Transaction
import com.ayush.cashcrunch.domain.model.User
import com.ayush.cashcrunch.domain.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : MainRepository {

    override fun getAllTransactions(): Flow<Response<List<Transaction>>> = flow {
        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid
            val transactions = mutableListOf<Transaction>()


            var totalIncome = 0
            var totalSpends = 0
            var netBalance = 0
            firestore.collection(TRANSACTION_COLLECTIONS)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            if (document.exists()) {
                                if (document.data.containsValue(currentUserId)) {
                                    val title = document.data["title"].toString()
                                    val category = document.data["category"].toString()
                                    val type = document.data["type"]
                                    val amount = document.data["amount"].toString()
                                    val date = document.data["date"].toString()

                                    if (type == "Income") {
                                        totalIncome += amount.toInt()
                                    } else {
                                        totalSpends += amount.toInt()
                                    }

                                    netBalance = totalIncome - totalSpends


                                    transactions.add(
                                        Transaction(
                                            title = title,
                                            type = if (type == "Income") Expense.INCOME else Expense.SPEND,
                                            category = category,
                                            amount = amount,
                                            userId = currentUserId!!,
                                            date = date
                                        )
                                    )
                                } else {
                                    continue
                                }
                            } else {
                                continue
                            }
                        }
                    }
                }.await()

            firestore.collection(EXPENDITURE_COLLECTION)
                .document(currentUserId!!)
                .set(
                    hashMapOf(
                        "userId" to currentUserId,
                        "totalSpends" to totalSpends,
                        "totalIncome" to totalIncome,
                        "netBalance" to netBalance
                    )
                )
                .await()

            emit(Response.Success(transactions))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }

    override fun getUserDetails(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid!!

            val user = firestore.collection(USERS_COLLECTION)
                .document(currentUserId)
                .get()
                .await()

            val userObj: User = user.data.let {
                User(
                    userId = currentUserId,
                    name = it?.get("name").toString(),
                    password = it?.get("password").toString(),
                    email = it?.get("email").toString(),
                    photo = it?.get("photo").toString()
                )
            }

            emit(Response.Success(userObj))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }

    override fun getRecentTransactions(): Flow<Response<List<Transaction>>> = flow {
        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid
            val transactions = mutableListOf<Transaction>()

            firestore.collection(TRANSACTION_COLLECTIONS)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            if (document.exists()) {
                                if (document.data.containsValue(currentUserId)) {
                                    val title = document.data["title"].toString()
                                    val category = document.data["category"].toString()
                                    val type = document.data["type"]
                                    val amount = document.data["amount"].toString()
                                    val date = document.data["date"].toString()
                                    val time = document.data["time"].toString().toLong()

                                    transactions.add(
                                        Transaction(
                                            title = title,
                                            type = if (type == "Income") Expense.INCOME else Expense.SPEND,
                                            category = category,
                                            amount = amount,
                                            userId = currentUserId!!,
                                            date = date,
                                            time = time
                                        )
                                    )
                                } else {
                                    continue
                                }
                            } else {
                                continue
                            }
                        }
                    }
                }.await()

            emit(Response.Success(transactions))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }

    override fun addUserTransaction(transaction: Transaction): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid!!

            val transactionObj = hashMapOf(
                "userId" to currentUserId,
                "title" to transaction.title,
                "category" to transaction.category,
                "amount" to transaction.amount,
                "type" to if (transaction.type == Expense.INCOME) "Income" else "Expense",
                "date" to transaction.date,
                "time" to transaction.time,
            )

            firestore.collection(TRANSACTION_COLLECTIONS)
                .add(transactionObj)
                .await()

            emit(Response.Success(true))


        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }

    override fun getExpenditure(): Flow<Response<Expenditure>> = flow {
        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid!!

            val data = firestore.collection(EXPENDITURE_COLLECTION)
                .document(currentUserId)
                .get()
                .await()
                .data!!

            emit(
                Response.Success(
                    Expenditure(
                        userId = currentUserId,
                        totalSpends = data["totalSpends"].toString(),
                        totalIncome = data["totalIncome"].toString(),
                        netBalance = data["netBalance"].toString()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }

    override fun getMonthlyExpenditureData(): Flow<Response<List<Expenditure>>> = flow {

        try {
            emit(Response.Loading)

            val currentUserId = auth.currentUser?.uid!!

            var spends = 0
            var income = 0

            val list = mutableListOf<Expenditure>()

            firestore.collection(TRANSACTION_COLLECTIONS)
                .orderBy("time", Query.Direction.DESCENDING)
                .whereGreaterThan(
                    "time",
                    Instant.ofEpochMilli(System.currentTimeMillis())
                        .atZone(ZoneId.systemDefault())
                        .minusMonths(1L)
                        .toEpochSecond()
                )
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result.documents) {
                            if (document.exists()) {
                                if (document.data!!.containsValue(currentUserId)) {

                                    val amount = document.data!!["amount"].toString().toInt()
                                    val type =
                                        if (document.data!!["type"].toString() == "Income") "Income" else "Expense"
                                    if (type == "Income") {
                                        income += amount
                                    } else {
                                        spends += amount
                                    }
                                    list.add(
                                        Expenditure(
                                            userId = currentUserId,
                                            totalSpends = spends.toString(),
                                            totalIncome = income.toString(),
                                            netBalance = (income - spends).toString()
                                        )
                                    )
                                } else {
                                    continue
                                }
                            } else {
                                continue
                            }
                        }
                    }
                }
                .await()

            emit(
                Response.Success(
                    list
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unknown error has occurred!"))
        }
    }
}