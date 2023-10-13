package com.ayush.cashcrunch.domain.use_cases.AuthUseCase

import com.ayush.cashcrunch.domain.repository.AuthRepository
import javax.inject.Inject


class SigninUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ) = authRepository.signIn(email, password)
}