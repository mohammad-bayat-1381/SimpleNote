package com.example.simplenote.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.simplenote.data.remote.NoteApi
import com.example.simplenote.domain.model.Note
import com.example.simplenote.domain.model.NoteRequest
import com.example.simplenote.domain.model.UpdateNoteRequest
import com.example.simplenote.presentation.home.NotePagingSource

class NoteRepository(private val api: NoteApi) {
//    suspend fun getNotes() = api.getNotes()
    suspend fun addNote(note: NoteRequest) = api.createNote(note)
    suspend fun deleteNote(noteId: Int) = api.deleteNote(noteId)
//    fun getNotesPager(query: String): Pager<Int, Note> {
//        return Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = { NotePagingSource(api, query) }
//        )
//    }
    suspend fun getNoteById(noteId: Int): Note {
        return api.getNoteById(noteId)
    }
    fun getNotesPager(query: String): Pager<Int, Note> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { NotePagingSource(api, query) }
        )
    }

    suspend fun updateNote(id: Int, title: String, description: String) {
        api.updateNote(id, UpdateNoteRequest(title, description))
    }

}