package com.example.impl.di


import androidx.compose.runtime.compositionLocalOf
import com.example.impl.presentation.viewmodel.AuthViewModelFactory

val LocalAuthViewModelFactory = compositionLocalOf<AuthViewModelFactory> {
    error("No AuthViewModelFactory provided!")
}