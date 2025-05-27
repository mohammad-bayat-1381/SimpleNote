package com.example.simplenote.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplenote.data.remote.RetrofitInstance
import com.example.simplenote.data.repository.AuthRepository

class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AuthRepository(RetrofitInstance.api)
        return AuthViewModel(repository) as T
    }
}
