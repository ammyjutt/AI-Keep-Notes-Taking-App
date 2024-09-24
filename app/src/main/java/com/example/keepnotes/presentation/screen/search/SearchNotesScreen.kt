package com.example.keepnotes.presentation.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.keepnotes.navigation.screen.Screen
import com.example.keepnotes.presentation.component.SearchScreenTopBar
import com.example.keepnotes.presentation.screen.allnotes.NoteCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.keepnotes.ui.theme.BackgroundCategory1
import com.example.keepnotes.ui.theme.BackgroundCategory2
import com.example.keepnotes.ui.theme.BackgroundCategory3
import com.example.keepnotes.ui.theme.BackgroundCategory4
import com.example.keepnotes.ui.theme.BackgroundCategory5
import com.example.keepnotes.ui.theme.BackgroundCategory6
import com.example.keepnotes.ui.theme.DIMENS_8dp













import androidx.compose.foundation.layout.*

import com.example.keepnotes.ui.theme.*












@Composable
fun SearchNotesScreen(
    searchNotesViewModel: SearchNotesViewModel = hiltViewModel(),
    navController: NavController,
    category: String? = "All", // Add category parameter
) {
    val context = LocalContext.current
    val allNotes by searchNotesViewModel.searchNotesList.collectAsState()
    var colorCounter = 0




    val filteredNotes = allNotes.filter { noteResponse ->
        val note = noteResponse.item

        when (category?.lowercase()) {
            "all" -> note?.category?.lowercase() != "deleted" && note?.category?.lowercase() != "archived"
            "deleted" -> note?.category?.lowercase() == "deleted"
            "archived" -> note?.category?.lowercase() == "archived"
            "reminder" -> note?.category?.lowercase() == "reminder"

            else -> false // In case the category is something else or null
        }
    }






    // Define the list of category background colors
    val backgroundColors = listOf(
        BackgroundCategory1,
        BackgroundCategory2,
        BackgroundCategory3,
        BackgroundCategory4,
        BackgroundCategory5,
        BackgroundCategory6
    )

    Scaffold(
        topBar = {
            SearchScreenTopBar(
                navController,
                onQueryChanged = {
                    searchNotesViewModel.onSearchTextChange(it)
                },
                onClearQuery = {
                    searchNotesViewModel.onSearchTextChange("")
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(DIMENS_8dp),
                horizontalArrangement = Arrangement.spacedBy(DIMENS_8dp),
                verticalItemSpacing = DIMENS_8dp
            ) {
                items(filteredNotes, key = { it.key!! }) { item ->
                    val backgroundColor = backgroundColors[colorCounter % backgroundColors.size]
                    colorCounter++

                    NoteCard(
                        item = item,
                        isSelected = false,
                        onClick = {
                            navController.navigate(Screen.EditNote.passNoteId(noteId = "${item.key}"))
                        },
                        backgroundColor = backgroundColor,
                        onLongClick = {

                        }
                    )
                }
            }
        }
    }
}
