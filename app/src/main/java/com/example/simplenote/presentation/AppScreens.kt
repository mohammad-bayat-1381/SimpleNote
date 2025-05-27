package com.example.simplenote.presentation.navigation

sealed class AppScreens(val route: String) {
    object Onboarding : AppScreens("onboarding")
    object Login : AppScreens("login")
    object Register : AppScreens("register")
}
