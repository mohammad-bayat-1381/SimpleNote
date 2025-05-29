package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.NoteApi
import com.example.simplenote.domain.model.NoteRequest

class NoteRepository(private val api: NoteApi) {
    suspend fun getNotes() = api.getNotes()

    suspend fun addNote(note: NoteRequest) = api.createNote(note)
}
