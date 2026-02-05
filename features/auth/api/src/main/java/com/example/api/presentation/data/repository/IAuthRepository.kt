package com.example.api.presentation.data.repository

import com.example.api.presentation.data.models.Credentials
import com.example.api.presentation.data.models.RegistrationData
import com.example.api.presentation.data.models.Users
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun login(credensial: Credentials): Result<Users>
    suspend fun register(registrationData: RegistrationData): Result<Users>
    suspend fun logout(): Result<Unit>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    fun getCurrentUser(): Users?
    fun observeAuthState(): Flow<Users?>
}