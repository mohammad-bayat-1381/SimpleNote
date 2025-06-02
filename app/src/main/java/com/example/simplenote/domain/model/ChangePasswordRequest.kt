package com.example.simplenote.domain.model

data class ChangePasswordRequest(
    val old_password: String,
    val new_password: String
)
