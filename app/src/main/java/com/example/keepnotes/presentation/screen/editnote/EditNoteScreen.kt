package com.example.keepnotes.presentation.screen.editnote


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.keepnotes.presentation.common.ProgressIndicator
import com.example.keepnotes.presentation.component.EditNoteBottomBar
import com.example.keepnotes.presentation.component.KeepNoteLinkDialog
import com.example.keepnotes.presentation.component.KeepNotePanel
import com.example.keepnotes.ui.theme.DIMENS_40dp
import com.example.keepnotes.ui.theme.GrayTextColor
import com.example.keepnotes.utils.canGoBack
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Arrangement // Import this line
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.filled.ArrowDropDown




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: String = "-1",
    editNoteViewModel: EditNoteViewModel = hiltViewModel()
) {
    val uiState by editNoteViewModel.uiState.collectAsState()
    val richTextState = rememberRichTextState()
    val titleTextState = remember { mutableStateOf(uiState.title) }
    val categoryTextState = remember { mutableStateOf(uiState.category) } // Add this line
    val openLinkDialog = remember { mutableStateOf(false) }
    var isShowTextEditorPanel by remember { mutableStateOf(false) }




    // List of categories
    val categories = listOf("Work", "Journal", "Reminder", "Password", "Health", "Others") // Example categories
    var expanded by remember { mutableStateOf(false) } // State to control dropdown visibility


    LaunchedEffect(noteId) {
        if (noteId != "-1") {
            editNoteViewModel.onEvent(EditNoteEvent.GetNote(noteId))
        }
    }

    LaunchedEffect(uiState.note) {
        titleTextState.value = uiState.title
        richTextState.setHtml(uiState.note)
        categoryTextState.value = uiState.category // Add this line
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "back arrow",
                        tint = GrayTextColor,
                        modifier = Modifier
                            .size(DIMENS_40dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                if (navController.canGoBack) {
                                    navController.popBackStack()
                                }
                            }
                    )

                    // Updated dropdown box to match the RoundedDropdown design
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(IntrinsicSize.Min)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                            .clickable { expanded = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = categoryTextState.value.ifEmpty { "Category" },
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                tint = Color.Black
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(16.dp))
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        categoryTextState.value = category
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Color(0xFFF2F2F2),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(vertical = 4.dp, horizontal = 8.dp)
                                        .height(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (isShowTextEditorPanel) {
                KeepNotePanel(
                    state = richTextState,
                    openLinkDialog = openLinkDialog,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .padding(horizontal = 20.dp),
                    onCloseEditor = {
                        isShowTextEditorPanel = !isShowTextEditorPanel
                    }
                )
            } else {
                EditNoteBottomBar(
                    updatedAt = System.currentTimeMillis(),
                    isShowTextEditorPanel = {
                        isShowTextEditorPanel = !isShowTextEditorPanel
                    }
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(it)


        ) {
            if (uiState.isLoading) {
                ProgressIndicator()
            } else {


                Spacer(modifier = Modifier.height(4.dp))


                EditableTextField(
                    initialText = titleTextState,
                    placeholderText = "Title",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp, vertical = 0.dp),
                    onTextChanged = { newText ->
                        titleTextState.value = newText
                    }
                )

                RichTextEditor(
                    state = richTextState,
                    placeholder = {
                        Text(text = "Note", color = Color.Gray)
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Normal),
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        textColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        placeholderColor = Color.Gray.copy(alpha = .6f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (openLinkDialog.value)
                Dialog(
                    onDismissRequest = {
                        openLinkDialog.value = false
                    }
                ) {
                    KeepNoteLinkDialog(
                        state = richTextState,
                        openLinkDialog = openLinkDialog
                    )
                }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            val timestamp = System.currentTimeMillis()
            if (titleTextState.value.isNotEmpty() || richTextState.annotatedString.text.isNotEmpty()) {
                if (noteId == "-1") {
                    editNoteViewModel.onEvent(
                        EditNoteEvent.AddNote(
                            title = titleTextState.value,
                            note = richTextState.toHtml(),

                            timestamp = timestamp,
                            category = categoryTextState.value,

                        )
                    )
                } else {
                    editNoteViewModel.onEvent(
                        EditNoteEvent.UpdateNote(
                            title = titleTextState.value,
                            note = richTextState.toHtml(),
                            category = categoryTextState.value, // Add category here
                            timestamp = timestamp
                        )
                    )
                }
            }
        }
    }
}





@Composable
fun EditableTextField(
    initialText: MutableState<String>,
    placeholderText: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier // Added modifier parameter with a default value
) {
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        value = initialText.value,
        onValueChange = {
            initialText.value = it
            onTextChanged(it)
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = if (placeholderText == "Title")
                    MaterialTheme.typography.headlineSmall.copy(color = GrayTextColor.copy(alpha = 0.5f))
                else
                    MaterialTheme.typography.titleMedium.copy(color = GrayTextColor.copy(alpha = 0.5f)),
            )
        },
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier // Applied the passed modifier here
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isKeyboardVisible = it.isFocused
                if (it.isFocused) {
                    keyboardController?.show()
                }
            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
//            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
            focusedBorderColor = Color.Transparent,

//            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedBorderColor = Color.Transparent ,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                isKeyboardVisible = false
            }
        )
    )

    DisposableEffect(Unit) {
        onDispose {
            if (isKeyboardVisible) {
                isKeyboardVisible = false
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditNoteScreenPreview() {
    EditNoteScreen(navController = rememberNavController())
}