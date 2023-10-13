package com.ayush.cashcrunch.domain.repository

import com.ayush.cashcrunch.core.Response
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    fun isUserAuthenticated(): Boolean

    fun getFirebaseAuthState(): Flow<Boolean>

    fun getCurrentUserId(): String

    fun signIn(
        email: String,
        password: String
    ): Flow<Response<Boolean>>

    fun signUp(
        name: String,
        email: String,
        password: String
    ): Flow<Response<Boolean>>

    fun signOut(): Flow<Response<Boolean>>
}