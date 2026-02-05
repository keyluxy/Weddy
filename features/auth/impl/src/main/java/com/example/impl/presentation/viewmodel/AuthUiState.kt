package com.example.impl.presentation.viewmodel

import com.example.api.presentation.data.models.Users

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val users: Users) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}