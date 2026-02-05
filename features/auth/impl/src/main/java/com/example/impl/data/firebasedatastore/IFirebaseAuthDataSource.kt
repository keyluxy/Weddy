package com.example.impl.data.firebasedatastore

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface IFirebaseAuthDataSource {
    suspend fun login(email: String, password: String): FirebaseUser
    suspend fun register(email: String, password: String, displayName: String?): FirebaseUser
    fun logout()
    suspend fun sendPasswordResentEmail(email: String)
    fun getCurrentUser(): FirebaseUser?
    fun observeAuthState(): Flow<FirebaseUser?>
}