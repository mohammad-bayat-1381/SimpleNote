@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.home.HomeViewModel

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val note = viewModel.getNoteById(noteId)
    var showDeleteDialog by remember { mutableStateOf(false) }

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
                        showDeleteDialog = true
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

        // Confirmation dialog for deletion
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirm Delete") },
                text = { Text("Are you sure you want to delete this note? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteNote(noteId)
                            showDeleteDialog = false
                            onBack()
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
