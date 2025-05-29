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

    fun fetchNotes() {
        viewModelScope.launch {
            try {
                val response = repository.getNotes()
                _notes.value = response.results
            } catch (e: Exception) {
                // Handle error appropriately
                _notes.value = emptyList()
            }
        }
    }
}
