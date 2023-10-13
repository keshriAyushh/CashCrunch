package com.ayush.cashcrunch.di

import com.ayush.cashcrunch.data.repository.AuthRepositoryImpl
import com.ayush.cashcrunch.data.repository.MainRepositoryImpl
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.AuthState
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.AuthUseCase
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.GetCurrentUserId
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.IsUserSignedIn
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.SignOutUseCase
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.SigninUseCase
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.SignupUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.AddTransactionUseCase.AddTransactionUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase.GetAllTransactionsUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase.GetHistoryUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HistoryUseCase.GetMonthlyExpenditureUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase.GetExpenditureUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase.GetRecentTransactionUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase.GetUserDetailsUseCase
import com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase.HomeUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun providesAuthRepository(
        auth: FirebaseAuth, firestore: FirebaseFirestore
    ) =
        AuthRepositoryImpl(auth, firestore)

    @Singleton
    @Provides
    fun providesAuthUseCase(
        authRepositoryImpl: AuthRepositoryImpl
    ) = AuthUseCase(
        isUserSignedIn = IsUserSignedIn(authRepository = authRepositoryImpl),
        signInUseCase = SigninUseCase(authRepository = authRepositoryImpl),
        signUpUseCase = SignupUseCase(authRepository = authRepositoryImpl),
        signOutUseCase = SignOutUseCase(authRepository = authRepositoryImpl),
        authState = AuthState(authRepository = authRepositoryImpl),
        getCurrentUserId = GetCurrentUserId(authRepository = authRepositoryImpl)
    )

    @Singleton
    @Provides
    fun providesUserRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ) = MainRepositoryImpl(auth, firestore)

    @Singleton
    @Provides
    fun providesHomeUseCase(
        mainRepositoryImpl: MainRepositoryImpl
    ) = HomeUseCase(
        GetUserDetailsUseCase(mainRepositoryImpl),
        GetRecentTransactionUseCase(mainRepositoryImpl),
        GetExpenditureUseCase(mainRepositoryImpl)
    )

    @Singleton
    @Provides
    fun providesHistoryUseCase(
        mainRepositoryImpl: MainRepositoryImpl
    ) = GetHistoryUseCase(
        GetAllTransactionsUseCase(mainRepositoryImpl),
        GetMonthlyExpenditureUseCase(mainRepositoryImpl)
    )

    @Singleton
    @Provides
    fun providesAddTransactionUseCase(
        mainRepositoryImpl: MainRepositoryImpl
    ) = AddTransactionUseCase(mainRepositoryImpl)

}