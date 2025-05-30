package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.NoteApi
import com.example.simplenote.domain.model.FilterResponse
import com.example.simplenote.domain.model.Note
import com.example.simplenote.domain.model.NoteRequest

class NoteRepository(private val api: NoteApi) {
    suspend fun getNotes() = api.getNotes()

    suspend fun addNote(note: NoteRequest) = api.createNote(note)

    suspend fun deleteNote(noteId: Int) = api.deleteNote(noteId)

    suspend fun filterNotes(query: String): FilterResponse {
        return api.filterNotes(query)
    }
}
