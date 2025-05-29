package com.example.simplenote.domain.model

data class TokenResponse(
    val access: String,
    val refresh: String? = null // optional
)
