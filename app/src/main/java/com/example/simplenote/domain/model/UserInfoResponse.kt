package com.example.simplenote.domain.model

data class UserInfoResponse(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String
)
