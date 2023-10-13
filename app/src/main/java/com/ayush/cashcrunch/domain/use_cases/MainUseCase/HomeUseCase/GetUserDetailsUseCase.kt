package com.ayush.cashcrunch.domain.use_cases.MainUseCase.HomeUseCase

import com.ayush.cashcrunch.domain.repository.MainRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke() = mainRepository.getUserDetails()
}