package com.example.simplenote.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val token = repository.login(email, password)
            if (token != null) {
                _authState.value = AuthState.Success(token)
            } else {
                _authState.value = AuthState.Error("Login failed. Check credentials.")
            }
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val isSuccessful = repository.register(firstName, lastName, username, email, password)
            if (isSuccessful) {
                _authState.value = AuthState.RegisterSuccess
            } else {
                _authState.value = AuthState.Error("Registration failed. Try again.")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
