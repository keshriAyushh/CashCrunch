package com.ayush.cashcrunch.domain.use_cases.AuthUseCase

import com.ayush.cashcrunch.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserSignedIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.isUserAuthenticated()
}