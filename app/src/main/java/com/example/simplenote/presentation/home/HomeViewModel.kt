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
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _forceRefresh = MutableStateFlow(false)

    val notes: StateFlow<PagingData<Note>> = combine(
        _searchQuery,
        _forceRefresh
    ) { query, _ ->
        query
    }.flatMapLatest { query ->
        repository.getNotesPager(query).flow
    }.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        _forceRefresh.value = !_forceRefresh.value // Toggle to force pager refresh
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                performSearch() // Refresh after deletion
            } catch (_: Exception) {
            }
        }
    }

    fun refreshNotes() {
        performSearch() // Call after returning from add screen
    }

    suspend fun getNoteById(noteId: Int): Note? {
        return try {
            repository.getNoteById(noteId)
        } catch (e: Exception) {
            null
        }
    }
}
