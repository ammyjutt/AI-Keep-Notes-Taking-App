package com.example.keepnotes.presentation.screen.allnotes

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.keepnotes.domain.model.RealtimeModelResponse
import com.example.keepnotes.navigation.screen.Screen
import com.example.keepnotes.presentation.common.ProgressIndicator
import com.example.keepnotes.presentation.component.HomeScreenTopBar
import com.example.keepnotes.presentation.component.SelectedTopBar
import com.example.keepnotes.ui.theme.BottomBarBackgroundColor
import com.example.keepnotes.ui.theme.CardBorder
import com.example.keepnotes.ui.theme.DIMENS_12dp
import com.example.keepnotes.ui.theme.DIMENS_16dp
import com.example.keepnotes.ui.theme.DIMENS_1dp
import com.example.keepnotes.ui.theme.DIMENS_3dp
import com.example.keepnotes.ui.theme.DIMENS_40dp
import com.example.keepnotes.ui.theme.DIMENS_8dp
import com.example.keepnotes.ui.theme.SelectedCardBorder
import com.example.keepnotes.ui.theme.TEXT_SIZE_18sp
import com.example.keepnotes.ui.theme.UndoTextColor
import com.example.keepnotes.utils.showToast
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.keepnotes.MainActivity
import com.example.keepnotes.ui.theme.DIMENS_32dp
import com.example.keepnotes.ui.theme.*


@Composable
fun AllNotesScreen(
    openDrawer: () -> Unit,
    navController: NavController,
    category: String? = "All", // Add category parameter
    allNotesViewModel: AllNotesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var isInSelectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }
    val resetSelectionMode: () -> Unit by rememberUpdatedState {
        isInSelectionMode = false
        selectedItems.clear()
    }




// BackHandler for selection mode
    BackHandler(enabled = isInSelectionMode) {
        resetSelectionMode()
    }



    LaunchedEffect(isInSelectionMode, selectedItems.size) {
        if (isInSelectionMode && selectedItems.isEmpty()) {
            isInSelectionMode = false
        }
    }

    val allNotes by allNotesViewModel.allNotesList.collectAsState()



    Log.d("@@@@@@AllNotesScreen", "Category:$category")




    Spacer(modifier = Modifier.height(40.dp))






    val filteredNotes = allNotes.item.filter { noteResponse ->
        val note = noteResponse.item

        when (category?.lowercase()) {
            "all" -> note?.category?.lowercase() != "deleted" && note?.category?.lowercase() != "archived"
            "deleted" -> note?.category?.lowercase() == "deleted"
            "archived" -> note?.category?.lowercase() == "archived"
            "reminder" -> note?.category?.lowercase() == "reminder"
            "work" -> note?.category?.lowercase() == "work"
            "journal" -> note?.category?.lowercase() == "journal"
            "health" -> note?.category?.lowercase() == "health"
            "password" -> note?.category?.lowercase() == "password"
            "others" -> note?.category?.lowercase() == "others"


            else -> false // In case the category is something else or null
        }
    }



    var changeView by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val categoryColorMap = mapOf(
        "Work" to BackgroundCategory1,
        "Journal" to BackgroundCategory2,
        "Health" to BackgroundCategory3,
        "Password" to BackgroundCategory4,
        "Reminder" to BackgroundCategory5,
        "Others" to BackgroundCategory6,
        "Archive" to BackgroundCategory3,
        "Deleted" to BackgroundCategory2
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    actionColor = UndoTextColor,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        topBar = {
            if (isInSelectionMode) {
                SelectedTopBar(
                    onClickAction = resetSelectionMode,
                    onClickMenu = { },

                    onDelete = {
                        selectedItems.forEach { note ->
                            allNotesViewModel.deleteNote(note)

                        }
                        resetSelectionMode()
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Note Deleted Successfully..",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                            when (result) {
                                SnackbarResult.ActionPerformed -> Log.d("SnackbarResult", "ActionPerformed")
                                SnackbarResult.Dismissed -> Log.d("SnackbarResult", "Dismissed")
                            }
                        }
                    },
                    onMakeCopy = {

                        selectedItems.forEach { note ->
                            allNotesViewModel.makeCopyNote(note)
                        }

                        resetSelectionMode()
                    },
                    onArchive = {

                        selectedItems.forEach { note ->
                            allNotesViewModel.archiveNote(note)
                        }

                        resetSelectionMode()


                    },

                    selectItemCount = selectedItems.size
                )
            } else {
                HomeScreenTopBar(
                    onClickAction = { openDrawer() },
                    onSearch = { navController.navigate(Screen.Search.route) },
                    onChangeView = { changeView = !changeView },
                    navController = navController
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        bottomBar = { /* BottomBar(navController = navController) */ },
        floatingActionButton = {
            if (category == "All") {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Existing FAB on the right


                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.EditNote.passNoteId(noteId = "-1")) },
                        backgroundColor = Color(0xFF2C2C2C), // Dark color
                        contentColor = Color.White, // Icon color
                        shape = RoundedCornerShape(50.dp), // More rounded
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = DIMENS_16dp, bottom = DIMENS_16dp)
                            .width(90.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "New Note",
                            modifier = Modifier
                                .size(44.dp) // Increased size of the icon
                                .padding(8.dp), // Padding to center the icon in the button
                            tint = Color.White // Ensure the icon color is white
                        )
                    }





                }
            }
        },
        isFloatingActionButtonDocked = true
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (allNotes.isLoading) {
                ProgressIndicator()
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(if (changeView) 1 else 2),
                    modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(DIMENS_8dp),
                    verticalItemSpacing = DIMENS_8dp
                ) {
                    items(filteredNotes, key = { it.key!! }) { item ->
                        // Select a color based on the counter and increment it
                        val noteCategory = item.item?.category ?: "Others" // Default to "Others" if category is null
                        val backgroundColor = categoryColorMap[noteCategory] ?: BackgroundCategory6 // Default to BackgroundCategory6 if the category is not in the map



                        val isSelected = selectedItems.contains(item.key)
                        NoteCard(
                            item = item,
                            isSelected = isSelected,
                            backgroundColor = backgroundColor, // Pass the color to NoteCard
                            onClick = {
                                if (isInSelectionMode) {
                                    if (isSelected) selectedItems.remove(item.key)
                                    else selectedItems.add(item.key!!)
                                } else {
                                    navController.navigate(Screen.EditNote.passNoteId(noteId = item.key!!))
                                }
                            },
                            onLongClick = {
                                if (isInSelectionMode) {
                                    if (isSelected) selectedItems.remove(item.key)
                                    else selectedItems.add(item.key!!)
                                } else {
                                    isInSelectionMode = true
                                    selectedItems.add(item.key!!)
                                }
                            }
                        )
                    }
                }
            }
            if (allNotes.error.isNotEmpty()) {
                context.showToast(allNotes.error, Toast.LENGTH_LONG)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    item: RealtimeModelResponse,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelected: Boolean,
    backgroundColor: Color // Accept background color as a parameter
) {
    val richTextState = remember { RichTextState() }
    val note = remember(item.item?.note) { item.item?.note }

    LaunchedEffect(note) {
        note?.let { richTextState.setHtml(it) }
    }

    val border = if (isSelected) {
        BorderStroke(3.dp, SelectedCardBorder)
    } else {
        null
    }

    Card(
        border = border,
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor, // Use the passed background color
                    shape = RoundedCornerShape(size = DIMENS_8dp)
                )
                .padding(DIMENS_12dp)
        ) {
            Text(
                text = item.item?.title.orEmpty(),
                style = TextStyle(
                    fontSize = TEXT_SIZE_18sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold, // Change to bold
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Left
                )
            )

            RichText(
                state = richTextState,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            // Box to wrap the category label with a border
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.item?.category.orEmpty(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Left
                    )
                )
            }
        }
    }
}




