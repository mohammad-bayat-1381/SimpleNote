package com.example.simplenote.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simplenote.R
import com.example.simplenote.domain.model.Note
import com.example.simplenote.presentation.home.HomeViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.rememberLazyListState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddNoteClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onNoteClick: (noteId: Int) -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLastPage by viewModel.isLastPage.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { lastVisibleIndex ->
                if (lastVisibleIndex == notes.lastIndex && !isLastPage && !isLoading) {
                    viewModel.loadNextPage()
                }
            }
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
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddNoteClick()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.filterNotes(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search Notes") },
                singleLine = true
            )

            if (notes.isEmpty() && !isLoading) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    items(notes) { note ->
                        NoteCard(note = note, onClick = { onNoteClick(note.id) })
                    }

                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
}