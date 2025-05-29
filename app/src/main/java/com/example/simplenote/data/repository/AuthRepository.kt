package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.AuthService
import com.example.simplenote.domain.model.LoginRequest
import com.example.simplenote.domain.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import android.util.Log
import com.example.simplenote.data.local.TokenManager

class AuthRepository(
    private val api: AuthService,
    private val tokenManager: TokenManager
) {
    suspend fun login(username: String, password: String): String? = withContext(Dispatchers.IO) {
        try {
            val response = api.login(LoginRequest(username, password))
            tokenManager.saveAccessToken(response.access)  // ✅ This is now valid
            tokenManager.saveRefreshToken(response.refresh)
            response.access
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = api.register(
                RegisterRequest(
                    username = username,
                    password = password,
                    email = email,
                    first_name = firstName,
                    last_name = lastName
                )
            )
            if (!response.isSuccessful) {
                Log.e("Register", "Registration failed: ${response.errorBody()?.string()}")
            }
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("Register", "Registration exception: ${e.message}")
            false
        }
    }
}
