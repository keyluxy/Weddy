package com.example.api.presentation.data.models

data class Users(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val photo: String? = null,
    val isEmailVerified: Boolean,
)