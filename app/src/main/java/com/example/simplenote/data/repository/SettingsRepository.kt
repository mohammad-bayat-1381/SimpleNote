package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.AuthService
import com.example.simplenote.domain.model.ChangePasswordRequest
import com.example.simplenote.domain.model.UserInfoResponse
import retrofit2.Response

class SettingsRepository(private val api: AuthService) {

    suspend fun getUserInfo(): UserInfoResponse {
        return api.getUserInfo()
    }

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Response<Unit> {

        return api.changePassword(ChangePasswordRequest(oldPassword,newPassword))
    }
}
