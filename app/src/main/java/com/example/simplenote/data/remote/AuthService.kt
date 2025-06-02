package com.example.simplenote.data.remote

import com.example.simplenote.domain.model.ChangePasswordRequest
import com.example.simplenote.domain.model.LoginRequest
import com.example.simplenote.domain.model.LoginResponse
import com.example.simplenote.domain.model.RegisterRequest
import com.example.simplenote.domain.model.TokenResponse
import com.example.simplenote.domain.model.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/token/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body body: Map<String, String>): TokenResponse

    @GET("auth/userinfo/")
    suspend fun getUserInfo(): UserInfoResponse

    @POST("auth/change-password/")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): Response<Unit>



}
