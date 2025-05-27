package com.example.simplenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplenote.presentation.auth.AuthViewModel
import com.example.simplenote.presentation.auth.AuthViewModelFactory
import com.example.simplenote.presentation.navigation.AppScreens
import com.example.simplenote.presentation.ui.LoginScreen
import com.example.simplenote.presentation.ui.OnboardingScreen
import com.example.simplenote.presentation.ui.RegisterScreen
import com.example.simplenote.ui.theme.SimpleNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleNoteTheme {
                val navController = rememberNavController()
                val viewModel = AuthViewModelFactory().create(AuthViewModel::class.java)

                NavHost(navController = navController, startDestination = AppScreens.Onboarding.route) {
                    composable(AppScreens.Onboarding.route) {
                        OnboardingScreen(onGetStarted = {
                            navController.navigate(AppScreens.Login.route)
                        })
                    }
                    composable(AppScreens.Login.route) {
                        LoginScreen(viewModel = viewModel, onRegister = {
                            navController.navigate(AppScreens.Register.route)
                        })
                    }
                    composable(AppScreens.Register.route) {
                        RegisterScreen(onLoginClick = {
                            navController.navigate(AppScreens.Login.route)
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleNoteTheme {
        Greeting("Android")
    }
}