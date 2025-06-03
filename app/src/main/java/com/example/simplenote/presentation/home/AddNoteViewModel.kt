package com.example.simplenote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.repository.NoteRepository
import com.example.simplenote.domain.model.NoteRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteCreated = MutableStateFlow(false)
    val noteCreated: StateFlow<Boolean> = _noteCreated

    fun addNote(title: String, description: String) {
        viewModelScope.launch {
            try {
                noteRepository.addNote(NoteRequest(title, description))
                _noteCreated.value = true
            } catch (e: Exception) {
                _noteCreated.value = false
            }
        }
    }

    fun resetNoteCreated() {
        _noteCreated.value = false
    }
}