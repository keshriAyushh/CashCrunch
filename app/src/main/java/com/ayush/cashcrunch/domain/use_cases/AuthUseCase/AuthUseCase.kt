package com.ayush.cashcrunch.domain.use_cases.AuthUseCase

data class AuthUseCase(
    val isUserSignedIn: IsUserSignedIn,
    val authState: AuthState,
    val signInUseCase: SigninUseCase,
    val signUpUseCase: SignupUseCase,
    val signOutUseCase: SignOutUseCase,
    val getCurrentUserId: GetCurrentUserId
)
