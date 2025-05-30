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

//    fun fetchNotes() {
//        viewModelScope.launch {
//            try {
//                val response = repository.getNotes()
//                _notes.value = response.results
//            } catch (e: Exception) {
//                // Handle error appropriately
//                _notes.value = emptyList()
//            }
//        }
//    }

    private var allNotes: List<Note> = emptyList()

    fun fetchNotes() {
        viewModelScope.launch {
            val response = repository.getNotes()
            val fetchedNotes = response.results  // ✅ Extract just the list
            allNotes = fetchedNotes
            _notes.value = allNotes
        }
    }

    fun getNoteById(noteId: Int): Note? {
        return notes.value.find { it.id == noteId }
    }

//    fun filterNotes(query: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.filterNotes(query)
//                _notes.value = response.results  // Adjust this line if your API structure differs
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
//
    fun filterNotes(query: String) {
        _notes.value = if (query.isBlank()) {
            allNotes
        } else {
            allNotes.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                fetchNotes() // Refresh the notes list
            } catch (e: Exception) {
                // Handle error (e.g., show a Snackbar)
            }
        }
    }
}
