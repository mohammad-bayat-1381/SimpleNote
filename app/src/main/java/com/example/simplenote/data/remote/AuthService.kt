package com.example.simplenote.data.remote

import com.example.simplenote.domain.model.RegisterRequest
import com.example.simplenote.domain.model.UserInfoResponse
import com.example.simplenote.domain.model.LoginRequest
import com.example.simplenote.domain.model.LoginResponse

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val access: String, val refresh: String)

interface AuthService {
    @POST("api/auth/register/")
    suspend fun register(@Body request: RegisterRequest): UserInfoResponse

    @POST("api/auth/token/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/token/refresh/")
    suspend fun refreshToken(@Body refresh: Map<String, String>): LoginResponse

    @GET("api/auth/userinfo/")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfoResponse
}
