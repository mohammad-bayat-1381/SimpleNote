package com.example.simplenote.presentation.auth

data class ChangePasswordUiState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
