@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.home.AddNoteViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel,
    onNoteSaved: () -> Unit,
    onBackPressed: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val noteCreated by viewModel.noteCreated.collectAsState()

    LaunchedEffect(noteCreated) {
        if (noteCreated) {
            viewModel.resetNoteCreated()
            onNoteSaved()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Note") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.addNote(title, content) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Note")
            }
        }
    }
}
