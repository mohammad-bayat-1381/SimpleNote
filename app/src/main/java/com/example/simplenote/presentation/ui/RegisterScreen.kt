package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.auth.AuthState
import com.example.simplenote.presentation.auth.AuthViewModel

@Composable
fun RegisterScreen(viewModel: AuthViewModel, onLoginClick: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val state by viewModel.authState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Retype Password") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (password == confirmPassword) {
                viewModel.register(firstName, lastName, username, email, password)
            }
        }) {
            Text("Register")
        }

        TextButton(onClick = {
            viewModel.resetState()
            onLoginClick()
        }) {
            Text("Already have an account? Login here")
        }

        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.RegisterSuccess -> Text("Registration successful! Please log in.")
            is AuthState.Error -> Text("Error: ${(state as AuthState.Error).message}", color = MaterialTheme.colorScheme.error)
            else -> {}
        }
    }
}
