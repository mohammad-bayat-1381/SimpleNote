package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.auth.AuthState
import com.example.simplenote.presentation.auth.AuthViewModel

@Composable
fun ResetPasswordScreen(onResetSuccess: () -> Unit) {
    Column {
        Text("Create a New Password")
        // Add TextFields and Button
        Button(onClick = {
            // Assume reset logic is done here
            onResetSuccess()
        }) {
            Text("Reset Password")
        }
    }
}
