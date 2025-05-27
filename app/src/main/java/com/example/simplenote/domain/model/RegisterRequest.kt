package com.example.simplenote.domain.model

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val password: String
)