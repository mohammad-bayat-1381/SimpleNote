package com.example.simplenote.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.repository.SettingsRepository
import com.example.simplenote.domain.model.UserInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfoResponse?>(null)
    val userInfo: StateFlow<UserInfoResponse?> = _userInfo

    fun fetchUserInfo() {
        viewModelScope.launch {
            try {
                _userInfo.value = repository.getUserInfo()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
