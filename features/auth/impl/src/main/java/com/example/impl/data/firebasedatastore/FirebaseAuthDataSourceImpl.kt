package com.example.impl.data.firebasedatastore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IFirebaseAuthDataSource {

    override suspend fun login(email: String, password: String): FirebaseUser {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return result?.user ?: throw AuthException("пользователь не найден")
    }

    override suspend fun register(email: String, password: String, displayName: String?): FirebaseUser {
        val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        displayName?.let { name ->
            user.user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
            )?.await()
        }

        return user?.user ?: throw AuthException("Ошибка создания пользователя")
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun sendPasswordResentEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun observeAuthState(): Flow<FirebaseUser?> {
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser)
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }

        }
    }
}

class AuthException(message: String) : Exception(message)