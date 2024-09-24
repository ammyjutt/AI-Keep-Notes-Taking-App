package com.example.keepnotes.presentation.screen.simplenotes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.keepnotes.domain.model.ResultState
import com.example.keepnotes.domain.model.RealtimeModelResponse

@Composable
fun SimpleNotesScreen(
    simpleNotesViewModel: SimpleNotesViewModel = hiltViewModel()
) {
    val notesState by simpleNotesViewModel.notesState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (notesState) {
            is ResultState.Loading -> {
                Text("Loading...", style = MaterialTheme.typography.bodyLarge)
            }
            is ResultState.Failure -> {
                Text("Error: ${(notesState as ResultState.Failure).msg}", style = MaterialTheme.typography.bodyLarge)
            }
            is ResultState.Success -> {
                LazyColumn {
                    items((notesState as ResultState.Success<List<RealtimeModelResponse>>).data) { note ->
                        NoteItem(note)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: RealtimeModelResponse) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Title: ${note.item?.title.orEmpty()}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Content: ${note.item?.note.orEmpty()}", style = MaterialTheme.typography.bodyMedium)
    }
}
