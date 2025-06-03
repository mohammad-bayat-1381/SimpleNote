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

    // Use a refresh trigger that increments on each refresh
    private val _refreshTrigger = MutableStateFlow(0)

    val notes: Flow<PagingData<Note>> = combine(
        _searchQuery.debounce(300),  // 300ms debounce for search-as-you-type
        _refreshTrigger
    ) { query, _ -> query }
        .flatMapLatest { query ->
            repository.getNotesPager(query).flow
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    // Proper refresh mechanism
    fun refreshNotes() {
        _refreshTrigger.value++ // Increment to trigger refresh
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