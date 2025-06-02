package com.example.simplenote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplenote.data.repository.NoteRepository
import com.example.simplenote.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val notes: Flow<PagingData<Note>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            repository.getNotesPager(query).flow
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun refreshNotes() {
        // Create a copy of the current value to force refresh
        _searchQuery.value = _searchQuery.value
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                refreshNotes()
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    suspend fun getNoteById(noteId: Int): Note? {
        return try {
            repository.getNoteById(noteId)
        } catch (e: Exception) {
            null
        }
    }
}