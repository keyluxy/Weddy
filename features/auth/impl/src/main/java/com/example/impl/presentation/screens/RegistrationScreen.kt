package com.example.impl.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.impl.di.LocalAuthViewModelFactory
import com.example.impl.presentation.viewmodel.AuthUiState
import com.example.impl.presentation.viewmodel.AuthViewModel

@Composable
fun RegistrationScreen(
    onLoginScreenClick: () -> Unit,
    onMainScreenClick: (String) -> Unit,
    modifier: Modifier,
) {

    var emailState = remember { mutableStateOf("") }
    var passwordState = remember { mutableStateOf("") }
    val factory = LocalAuthViewModelFactory.current
    val viewModel: AuthViewModel = viewModel(factory = factory)
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onMainScreenClick(uiState.users.displayName ?: "Guest")
            viewModel.resetState()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState is AuthUiState.Loading) {
            CircularProgressIndicator()
        }

        if (uiState is AuthUiState.Error) {
            Text("Error: ${uiState.message}")
        }

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("email") }
        )

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") }
        )

        Button(
            onClick = { viewModel.register(emailState.value, passwordState.value, "") }
        ) {
            Text("Register")
        }

        Button(
            onClick = { onLoginScreenClick() }
        ) {
            Text("Go to login")
        }
    }
}