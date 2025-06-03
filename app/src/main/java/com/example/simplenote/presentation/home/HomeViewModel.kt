package com.example.simplenote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplenote.data.repository.NoteRepository
import com.example.simplenote.domain.model.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _refreshTrigger = MutableStateFlow(0)

    val notes: Flow<PagingData<Note>> = combine(
        _searchQuery.debounce(300),
        _refreshTrigger
    ) { query, _ -> query }
        .flatMapLatest { query ->
            repository.getNotesPager(query).flow
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun refreshNotes() {
        _refreshTrigger.value++
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                refreshNotes()
            } catch (_: Exception) {
                // Optionally log or handle error
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

    fun updateNote(id: Int, title: String, description: String) {
        viewModelScope.launch {
            try {
                repository.updateNote(id, title, description)
                // optionally trigger refresh here
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
