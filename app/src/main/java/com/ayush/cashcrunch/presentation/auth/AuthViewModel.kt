package com.ayush.cashcrunch.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayush.cashcrunch.core.Response
import com.ayush.cashcrunch.domain.use_cases.AuthUseCase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    val isUserAuthenticated get() = authUseCase.isUserSignedIn()

    val getCurrentUserId get() = authUseCase.getCurrentUserId()

    private val _signInState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signInState: StateFlow<Response<Boolean>> = _signInState

    private val _signOutState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signOutState: StateFlow<Response<Boolean>> = _signOutState

    private val _signUpState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signUpState: StateFlow<Response<Boolean>> = _signUpState

    private val _authState = MutableStateFlow<Boolean>(false)
    val authState: StateFlow<Boolean> = _authState



    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.signInUseCase(email, password)
                .collect {
                    _signInState.value = it
                }
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            authUseCase.signUpUseCase(email = email, password = password, name = name)
                .collect {
                    _signUpState.value = it
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCase.signOutUseCase().collect {
                _signOutState.value = it
                if(it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun getAuthState() {
        viewModelScope.launch {
            authUseCase.authState().collect {
                _authState.value = it
            }
        }
    }
}