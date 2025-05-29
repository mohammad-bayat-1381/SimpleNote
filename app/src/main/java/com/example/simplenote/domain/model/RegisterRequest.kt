package com.example.simplenote.domain.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val first_name: String,
    val last_name: String
)
