package com.example.simplenote.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.presentation.auth.AuthState
import com.example.simplenote.presentation.auth.AuthViewModel

@Composable
fun AddNoteScreen(onNoteSaved: () -> Unit) {
    Column {
        Text("Add Note Screen")
        Button(onClick = onNoteSaved) {
            Text("Save Note")
        }
    }
}
