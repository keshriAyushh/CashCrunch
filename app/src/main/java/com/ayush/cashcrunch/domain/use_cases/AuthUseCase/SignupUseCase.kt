package com.ayush.cashcrunch.domain.use_cases.AuthUseCase

import com.ayush.cashcrunch.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, name: String, password: String) =
        authRepository.signUp(name, email, password)
}