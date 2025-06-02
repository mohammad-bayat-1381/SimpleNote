package com.example.simplenote.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val response = settingsRepository.changePassword(oldPassword, newPassword)
                _message.value = if (response.isSuccessful) {
                    "Password changed successfully"
                } else {
                    response.errorBody()?.string() ?: "Failed to change password"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            }
        }
    }
}
