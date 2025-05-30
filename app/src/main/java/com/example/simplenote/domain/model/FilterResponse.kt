package com.example.simplenote.domain.model

data class FilterResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Note>
)
