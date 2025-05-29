package com.example.simplenote.data.remote

import com.example.simplenote.domain.model.Note
import retrofit2.http.GET

interface NoteApi {
    @GET("/api/notes/")
    suspend fun getNotes(): NoteListResponse
}

data class NoteListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Note>
)
