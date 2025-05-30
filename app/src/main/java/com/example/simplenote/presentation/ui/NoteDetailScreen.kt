@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.home.HomeViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val note = viewModel.getNoteById(noteId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note?.title ?: "Note Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteNote(noteId)
                        onBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Title", style = MaterialTheme.typography.labelMedium)
            Text(text = note?.title ?: "", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Content", style = MaterialTheme.typography.labelMedium)
            Text(text = note?.description ?: "", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
