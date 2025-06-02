package com.example.simplenote.presentation.navigation

sealed class AppScreens(val route: String) {
    object Onboarding : AppScreens("onboarding")
    object Login : AppScreens("login")
    object AddNote  : AppScreens("add_note")
    object Register : AppScreens("register")
    object NoteList  : AppScreens("note_list")
    object ResetPassword  : AppScreens("reset_password")
    object Home  : AppScreens("home")
    object Settings  : AppScreens("settings")
    object ChangePassword  : AppScreens("change_password")
    object NoteDetail : AppScreens("note_detail/{noteId}") {
        fun createRoute(noteId: String) = "note_detail/$noteId"
    }

}
