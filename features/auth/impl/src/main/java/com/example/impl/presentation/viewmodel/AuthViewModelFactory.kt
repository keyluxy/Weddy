package com.example.impl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api.presentation.data.repository.IAuthRepository
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == AuthViewModel::class.java) { "Unknown ViewModel class" }
        return AuthViewModel(authRepository) as T
    }
}