package com.example.simplenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplenote.data.local.TokenManager
import com.example.simplenote.data.remote.RetrofitInstance
import com.example.simplenote.data.repository.AuthRepository
import com.example.simplenote.data.repository.NoteRepository
import com.example.simplenote.data.repository.SettingsRepository
import com.example.simplenote.presentation.auth.AuthViewModel
import com.example.simplenote.presentation.auth.AuthViewModelFactory
import com.example.simplenote.presentation.auth.ChangePasswordViewModel
import com.example.simplenote.presentation.auth.ChangePasswordViewModelFactory
import com.example.simplenote.presentation.home.AddNoteViewModel
import com.example.simplenote.presentation.home.AddNoteViewModelFactory
import com.example.simplenote.presentation.ui.HomeScreen
import com.example.simplenote.presentation.home.HomeViewModel
import com.example.simplenote.presentation.home.HomeViewModelFactory
import com.example.simplenote.presentation.navigation.AppScreens
import com.example.simplenote.presentation.settings.SettingsViewModel
import com.example.simplenote.presentation.settings.SettingsViewModelFactory
import com.example.simplenote.presentation.ui.*
import com.example.simplenote.ui.theme.SimpleNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenManager = TokenManager(applicationContext)
        RetrofitInstance.init(tokenManager)
        val authRepository = AuthRepository(RetrofitInstance.api, tokenManager)
        val viewModel = AuthViewModelFactory(authRepository).create(AuthViewModel::class.java)

        setContent {
            SimpleNoteTheme {
                val navController = rememberNavController()

                val noteRepository = NoteRepository(RetrofitInstance.noteApi)
                val homeViewModelFactory = HomeViewModelFactory(noteRepository)
                val homeViewModel = homeViewModelFactory.create(HomeViewModel::class.java)

                val addNoteViewModel = AddNoteViewModelFactory(noteRepository).create(
                    AddNoteViewModel::class.java)

                val settingsRepo = SettingsRepository(RetrofitInstance.api)
                val settingsViewModel = SettingsViewModelFactory(settingsRepo).create(
                    SettingsViewModel::class.java)

                val changePasswordViewModel = ChangePasswordViewModelFactory(settingsRepo)
                    .create(ChangePasswordViewModel::class.java)

                NavHost(navController = navController, startDestination = AppScreens.Onboarding.route) {
                    composable(AppScreens.Onboarding.route) {
                        OnboardingScreen(onGetStarted = {
                            navController.navigate(AppScreens.Login.route)
                        })
                    }
                    composable(AppScreens.Login.route) {
                        LoginScreen(
                            viewModel = viewModel,
                            onRegister = { navController.navigate(AppScreens.Register.route) },
                            onForgotPassword = { navController.navigate(AppScreens.ResetPassword.route) },
                            onLoginSuccess = { navController.navigate(AppScreens.Home.route) }
                        )
                    }
                    composable(AppScreens.Register.route) {
                        RegisterScreen(
                            viewModel = viewModel,
                            onLoginClick = { navController.navigate(AppScreens.Login.route) },
                            onRegisterSuccess = { navController.navigate(AppScreens.Home.route) }
                        )
                    }
                    composable(AppScreens.ResetPassword.route) {
                        ResetPasswordScreen(onResetSuccess = { navController.navigate(AppScreens.Login.route) })
                    }
                    composable(AppScreens.Home.route) {
                        HomeScreen(
                            viewModel = homeViewModel,
                            onAddNoteClick = { navController.navigate(AppScreens.AddNote.route) },
                            onSettingsClick = { navController.navigate(AppScreens.Settings.route) },
                            onNoteClick = { noteId -> navController.navigate("note_detail/$noteId") }
                        )

                    }
                    composable(AppScreens.NoteList.route) {
                        NoteListScreen(onNoteClick = { noteId ->
                            navController.navigate("note_detail/$noteId")
                        })
                    }
                    composable("note_detail/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                        noteId?.let {
                            NoteDetailScreen(
                                noteId = it,
                                viewModel = homeViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(AppScreens.AddNote.route) {
                        AddNoteScreen(
                            viewModel = addNoteViewModel,
                            onNoteSaved = {
                                navController.popBackStack()
                            },
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(AppScreens.Settings.route) {
                        SettingsScreen(
                            viewModel = settingsViewModel,
                            navController = navController,
                            onLogoutConfirmed = {
                                tokenManager.clearTokens()
                                navController.navigate(AppScreens.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                    composable(AppScreens.ChangePassword.route) {
                        ChangePasswordScreen(
                            viewModel = changePasswordViewModel,
                            onBack = { navController.navigate(AppScreens.Login.route) {
                                popUpTo(AppScreens.Login.route) { inclusive = true }
                            }}
                        )
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