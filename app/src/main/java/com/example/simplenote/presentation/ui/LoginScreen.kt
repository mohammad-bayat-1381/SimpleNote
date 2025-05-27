package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.auth.AuthState
import com.example.simplenote.presentation.auth.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Let’s Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.login(email, password) }) {
            Text("Login")
        }

        TextButton(onClick = onRegister) {
            Text("Don’t have any account? Register here")
        }

        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> Text("Access token: ${(state as AuthState.Success).token}")
            is AuthState.Error -> Text("Error: ${(state as AuthState.Error).message}", color = MaterialTheme.colorScheme.error)
            else -> {}
        }
    }
}
