package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.auth.AuthState
import com.example.simplenote.presentation.auth.AuthViewModel

@Composable
fun NoteListScreen(onNoteClick: (String) -> Unit) {
    Column {
        Text("Note List Screen")
        Button(onClick = { onNoteClick("456") }) {
            Text("Open Note Detail")
        }
    }
}
