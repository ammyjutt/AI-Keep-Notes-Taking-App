package com.example.keepnotes.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.keepnotes.ui.theme.TEXT_SIZE_18sp


@Composable
fun DropdownMenuOptions(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onMakeCopy: () ->Unit,
    onArchive: () -> Unit
) {




    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss.invoke() }
        ) {
            DropdownMenuItem(onClick = {
                onArchive.invoke()
            }) {
                Text(
                    "Archive",
                    style = MaterialTheme.typography.body2.copy(fontSize = TEXT_SIZE_18sp)
                )
            }
            DropdownMenuItem(onClick = {
                onDelete.invoke()
            }) {
                Text(
                    "Delete",
                    style = MaterialTheme.typography.body2.copy(fontSize = TEXT_SIZE_18sp)
                )
            }
            DropdownMenuItem(onClick = {
                onMakeCopy.invoke()
            }) {
                Text(
                    "Copy",
                    style = MaterialTheme.typography.body2.copy(fontSize = TEXT_SIZE_18sp)
                )
            }

        }
    }
}