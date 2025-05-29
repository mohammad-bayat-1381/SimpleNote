package com.example.simplenote.data.remote

import com.example.simplenote.domain.model.Note
import com.example.simplenote.domain.model.NoteRequest
import retrofit2.http.*

interface NoteApi {
    @GET("/api/notes/")
    suspend fun getNotes(): NoteListResponse

    @POST("/api/notes/")
    suspend fun createNote(@Body note: NoteRequest): Note
}

data class NoteListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Note>
)
