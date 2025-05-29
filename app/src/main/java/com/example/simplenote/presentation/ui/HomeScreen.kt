// presentation/ui/HomeScreen.kt
package com.example.simplenote.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simplenote.R
import com.example.simplenote.presentation.home.HomeViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddNoteClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNotes()
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Add, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onSettingsClick,
                    icon = { Icon(Icons.Default.Add, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        if (notes.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ensure R.drawable.ic_empty exists or replace with a valid image
                Image(
                    painter = painterResource(id = R.drawable.ic_empty),
                    contentDescription = "Empty"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Start Your Journey", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Every big step starts with a small step.\nNote your first idea and start your journey!",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(notes) { note ->
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
