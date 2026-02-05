// AuthComponent.kt
package com.example.impl.di

import com.example.impl.presentation.viewmodel.AuthViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AuthModule::class,
        FirebaseModule::class
    ]
)
@Singleton  // Измените @AuthScope на @Singleton
interface AuthComponent {

    fun authViewModelFactory(): AuthViewModelFactory

    // Удалите createAuthViewModel() - он не нужен
    // Удалите inject() методы - они не нужны

    @Component.Builder
    interface Builder {
        fun authModule(module: AuthModule): Builder
        fun firebaseModule(module: FirebaseModule): Builder
        fun build(): AuthComponent
    }
}