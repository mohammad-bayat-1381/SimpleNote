package com.example.simplenote.data.remote

import com.example.simplenote.domain.model.LoginRequest
import com.example.simplenote.domain.model.RegisterRequest
import com.example.simplenote.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): retrofit2.Response<Unit>
}
