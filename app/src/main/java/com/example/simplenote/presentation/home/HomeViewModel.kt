package com.example.simplenote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.domain.model.Note
import com.example.simplenote.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    private var allNotes: List<Note> = emptyList()
    private var filteredNotes: List<Note> = emptyList()

    private var currentPage = 1
    private val pageSize = 10

    fun fetchNotes(reset: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getNotes()
            allNotes = response.results
            filteredNotes = allNotes
            currentPage = 1
            if (reset) _notes.value = emptyList()
            updateNotesPage()
            _isLoading.value = false
        }
    }

    fun filterNotes(query: String) {
        currentPage = 1
        filteredNotes = if (query.isBlank()) {
            allNotes
        } else {
            allNotes.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
        updateNotesPage()
    }

    fun loadNextPage() {
        val totalPages = (filteredNotes.size + pageSize - 1) / pageSize
        if (currentPage < totalPages) {
            currentPage++
            updateNotesPage()
        } else {
            _isLastPage.value = true
        }
    }

    private fun updateNotesPage() {
        val fromIndex = (currentPage - 1) * pageSize
        val toIndex = (fromIndex + pageSize).coerceAtMost(filteredNotes.size)
        _notes.value = filteredNotes.subList(0, toIndex) // Return items from start up to current page
        _isLastPage.value = toIndex >= filteredNotes.size
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                fetchNotes(reset = true)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getNoteById(noteId: Int): Note? {
        return notes.value.find { it.id == noteId }
    }
}
