package com.example.keepnotes.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.keepnotes.ui.theme.*
import androidx.compose.material3.Text


@Composable
fun SearchScreenTopBar(
    navController: NavController,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    var searchInput by remember { mutableStateOf("") }


    val SearchBarBackgroundColor = Color(0xFFB3E5FC)  // Replace with your desired color
    val InputTextColor = Color.Black
    val PlaceholderTextColor = Color.Gray


    Box(
        modifier = Modifier.padding(horizontal = DIMENS_16dp, vertical = DIMENS_8dp)
    ) {
        TopAppBar(
            backgroundColor = Color(0xFFE2E9FC),  // Match the color with HomeScreenTopBar
            contentColor = Color.Black,           // Set the content color
            actions = {
                IconButton(onClick = {
                    searchInput = ""
                    onClearQuery.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black,  // Ensure the icon color matches
                        modifier = Modifier.size(DIMENS_20dp)
                    )
                }
            },
            elevation = DIMENS_1dp,
            title = {
                SearchTextField(
                    text = searchInput,
                    placeholderText = "Search your notes"
                ) { newText ->
                    searchInput = newText
                    onQueryChanged(searchInput)
                }
            },
            modifier = Modifier
                .background(
                    color = Color(0xFFE2E9FC),  // Match the background color
                    shape = RoundedCornerShape(DIMENS_50dp)  // Match the rounded shape
                )
                .padding(horizontal = DIMENS_0dp),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black  // Ensure the icon color matches
                    )
                }
            }
        )
    }
}

@Composable
fun SearchTextField(
    text: String,
    placeholderText: String,
    onTextChanged: (String) -> Unit
) {
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black.copy(alpha = 0.5f), // Match the placeholder text color with black at 50% opacity
                    fontSize = TEXT_SIZE_18sp,
                    fontWeight = FontWeight.W300
                ),
            )
        },
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = Color.Black.copy(alpha = 0.5f), // Match the input text color with black at 50% opacity
            fontSize = TEXT_SIZE_18sp,
            fontWeight = FontWeight.W300
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { isKeyboardVisible = true },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFE2E9FC), // Match the search bar background color
            focusedBorderColor = Color.Transparent, // Transparent border to match the rounded shape
            unfocusedBorderColor = Color.Transparent, // Transparent border to match the rounded shape
            cursorColor = Color.Black // Black cursor color
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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isKeyboardVisible) {
                isKeyboardVisible = false
            }
        }
    }
}

