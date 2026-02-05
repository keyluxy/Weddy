package com.example.api.presentation.data.models

data class RegistrationData(
    val email: String,
    val password: String,
    val displayName: String? = null,
)