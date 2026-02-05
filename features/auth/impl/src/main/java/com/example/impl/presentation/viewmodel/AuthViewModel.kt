package com.example.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.presentation.data.models.Credentials
import com.example.api.presentation.data.models.RegistrationData
import com.example.api.presentation.data.repository.IAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject internal constructor(
    private val authRepository: IAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.observeAuthState().collectLatest { users ->
                if (users != null) {
                    _uiState.value = AuthUiState.Success(users = users)
                } else {
                    _uiState.value = AuthUiState.Idle
                }
            }
        }
    }

    fun login(emal: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = authRepository.login(Credentials(emal, password))

            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Login fail") }
            )
        }
    }

    fun register(email: String, password: String, displayName: String?) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = authRepository.register(RegistrationData(email, password, displayName))

            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Register failed") }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = authRepository.logout()

            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Idle },
                onFailure = { AuthUiState.Error(it.message ?: "Logout failed") }
            )
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            val result = authRepository.sendPasswordResetEmail(email)

            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Idle },
                onFailure = { AuthUiState.Error(it.message ?: "Reset password failed") }
            )
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}