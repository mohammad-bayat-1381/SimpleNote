package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.AuthService
import com.example.simplenote.domain.model.LoginRequest
import com.example.simplenote.domain.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val api: AuthService) {

    suspend fun login(email: String, password: String): String? = withContext(Dispatchers.IO) {
        try {
            val response = api.login(LoginRequest(email, password))
            response.access
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun register(
        firstname: String,
        lastname: String,
        username: String,
        email: String,
        password: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest(
                firstname = firstname,
                lastname = lastname,
                username = username,
                email = email,
                password = password
            )
            api.register(request)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
