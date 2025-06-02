package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.simplenote.presentation.navigation.AppScreens
import com.example.simplenote.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController, // pass from MainActivity
    onLogoutConfirmed: () -> Unit
) {
    val userInfo by viewModel.userInfo.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        userInfo?.let {
            Text("Full Name: ${it.first_name} ${it.last_name}")
            Text("Username: ${it.username}")
            Text("Email: ${it.email}")
        } ?: Text("Loading user info...")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(AppScreens.ChangePassword.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out", color = MaterialTheme.colorScheme.error)
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Log Out") },
                text = { Text("Are you sure you want to log out?") },
                confirmButton = {
                    TextButton(onClick = {
                        showLogoutDialog = false
                        onLogoutConfirmed()
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
