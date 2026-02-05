package com.example.impl.di

import com.example.api.presentation.data.repository.IAuthRepository
import com.example.impl.data.firebasedatastore.FirebaseAuthDataSource
import com.example.impl.data.firebasedatastore.IFirebaseAuthDataSource
import com.example.impl.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {  // Измените abstract class на class

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuthDataSource: FirebaseAuthDataSource
    ): IAuthRepository {
        return AuthRepositoryImpl(firebaseAuthDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        firebaseAuthDataSource: FirebaseAuthDataSource
    ): IFirebaseAuthDataSource {
        return firebaseAuthDataSource
    }
}