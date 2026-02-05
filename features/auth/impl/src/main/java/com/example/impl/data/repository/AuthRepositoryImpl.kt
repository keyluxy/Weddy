package com.example.impl.data.repository

import com.example.api.presentation.data.models.Credentials
import com.example.api.presentation.data.models.RegistrationData
import com.example.api.presentation.data.models.Users
import com.example.api.presentation.data.repository.IAuthRepository
import com.example.impl.data.firebasedatastore.FirebaseAuthDataSource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val dataStore: FirebaseAuthDataSource
) : IAuthRepository {
    override suspend fun login(credensial: Credentials): Result<Users> {
        return try {
            val firebaseUser = dataStore.login(credensial.email, credensial.password)
            Result.success(firebaseUser.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(registrationData: RegistrationData): Result<Users> {
        return try {
            val firebaseUser = dataStore.register(
                registrationData.email,
                registrationData.password,
                registrationData.displayName
            )
            Result.success(firebaseUser.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            dataStore.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Users? {
        return dataStore.getCurrentUser()?.toDomain()
    }


    override fun observeAuthState(): Flow<Users?> {
        return dataStore.observeAuthState().map { it?.toDomain() }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            dataStore.sendPasswordResentEmail(email = email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    private fun FirebaseUser.toDomain(): Users {
        return Users(
            id = this.uid,
            email = this.email ?: "",
            displayName = this.displayName,
            photo = this.photoUrl?.toString(),
            isEmailVerified = this.isEmailVerified,
        )
    }
}