package com.example.keepnotes.presentation.screen.ChatViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepnotes.R
import android.util.Log
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.keepnotes.ui.theme.DIMENS_16dp
import com.example.keepnotes.ui.theme.DIMENS_24dp
import com.example.keepnotes.ui.theme.DIMENS_8dp
import com.example.keepnotes.ui.theme.TEXT_SIZE_24sp

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = hiltViewModel()) {
    var userInput by remember { mutableStateOf("") }
    val response by chatViewModel.response.collectAsState()


    Log.d("XXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    chatViewModel.fetchAllNotes()

    Log.d("XXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")







    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .padding(
                    start = 60.dp,
                    top = DIMENS_24dp,
                    bottom = DIMENS_16dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.glitter_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = DIMENS_8dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF4E5053),
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    ) {
                        append("AI ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF9AA0A6),
                            fontWeight = FontWeight.Normal,
                            fontSize = 40.sp
                        )
                    ) {
                        append("Keep")
                    }
                },
                modifier = Modifier
                    .padding(start = DIMENS_8dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = userInput,
            onValueChange = {
                Log.d("ChatScreen", "User input changed: $it")
                userInput = it
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .padding(horizontal = 20.dp, vertical = 12.dp),
            decorationBox = { innerTextField ->
                if (userInput.isEmpty()) {
                    Text(
                        text = "Enter your query",
                        style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Log.d("ChatScreen", "Fetch Response button clicked with input: $userInput")
                chatViewModel.fetchResponse(userInput)
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text("Go ")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                contentDescription = "Go"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (response.isNotEmpty()) {
            Log.d("ChatScreen", "Displaying response from AI: $response")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.glitter_icon),
                    contentDescription = "Noto",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "AI Assistant",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = response,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}
