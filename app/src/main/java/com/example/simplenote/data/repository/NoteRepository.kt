package com.example.simplenote.data.repository

import com.example.simplenote.data.remote.NoteApi

class NoteRepository(private val api: NoteApi) {
    suspend fun getNotes() = api.getNotes()
}
