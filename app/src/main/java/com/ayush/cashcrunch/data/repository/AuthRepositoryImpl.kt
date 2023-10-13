package com.ayush.cashcrunch.data.repository

import com.ayush.cashcrunch.core.Constants.USERS_COLLECTION
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.model.User
import com.ayush.cashcrunch.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    var success = false

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun signIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        success = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password)
                .await()

            emit(Response.Success(true))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?: "An unknown error has occurred!"))
        }
    }

    override fun signUp(name: String, email: String, password: String): Flow<Response<Boolean>> =
        flow {
            success = false

            try {
                emit(Response.Loading)
                auth.createUserWithEmailAndPassword(email, password)
                    .await()

                val user = User(
                    userId = auth.currentUser?.uid!!,
                    name = name,
                    email = email,
                    password = password
                )

                firestore.collection(USERS_COLLECTION)
                    .document(user.userId)
                    .set(user)

                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "An unknown error has occurred!"))
            }
        }

    override fun signOut(): Flow<Response<Boolean>> = flow {
        success = false

        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unknown error has occurred!"))
        }
    }

    override fun getCurrentUserId() = auth.currentUser?.uid.toString()
}