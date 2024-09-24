package com.example.keepnotes.presentation.component

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.keepnotes.MainActivity
import com.example.keepnotes.R
import com.example.keepnotes.data.auth.GoogleUser
import com.example.keepnotes.data.auth.OneTapSignInWithGoogle
import com.example.keepnotes.data.auth.SignInResult
import com.example.keepnotes.data.auth.getUserFromTokenId
import com.example.keepnotes.data.auth.rememberOneTapSignInState
import com.example.keepnotes.presentation.common.ProgressIndicator
import com.example.keepnotes.presentation.screen.loginscreen.LoginViewModel
import com.example.keepnotes.presentation.screen.loginscreen.SignInViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.keepnotes.ui.theme.DIMENS_16dp
import com.example.keepnotes.ui.theme.DIMENS_1dp
import com.example.keepnotes.ui.theme.DIMENS_20dp
import com.example.keepnotes.ui.theme.DIMENS_34dp
import com.example.keepnotes.ui.theme.DIMENS_50dp
import com.example.keepnotes.ui.theme.DIMENS_8dp
import com.example.keepnotes.ui.theme.TEXT_SIZE_12sp
import com.example.keepnotes.ui.theme.TEXT_SIZE_14sp
import com.example.keepnotes.ui.theme.TEXT_SIZE_18sp
import com.example.keepnotes.ui.theme.TopBarBackgroundColor
import com.example.keepnotes.ui.theme.textOnWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@Composable
fun HomeScreenTopBar(
    onClickAction: () -> Unit,
    onSearch: () -> Unit,
    onChangeView: () -> Unit,
    navController: NavController
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(backgroundColor)
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<SignInViewModel>()
    val oneTapSignInState = rememberOneTapSignInState()
    var user: GoogleUser? by remember { mutableStateOf(null) }
    val userProfileUrl by viewModel.userProfileUrl.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var changeView by remember { mutableStateOf(false) }

    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = context.getString(R.string.web_client_id),
        rememberAccount = true,
        onTokenIdReceived = {
            user = getUserFromTokenId(tokenId = it)
            scope.launch {
                user?.sub?.let { userId ->
                    viewModel.saveUserId(userId)
                }
                user?.picture?.let { it1 ->
                    viewModel.saveUserProfileUrl(it1)
                }
            }
            viewModel.onSignInResult(SignInResult(data = user, errorMessage = null))
        },
        onDialogDismissed = {
            isLoading = false
            viewModel.onSignInResult(SignInResult(data = null, errorMessage = it))
        }
    )

    LaunchedEffect(key1 = user) {
        user?.let {
            Toast.makeText(
                context.applicationContext,
                "Sign in successful",
                Toast.LENGTH_SHORT
            ).show()
            val refresh = Intent(context, MainActivity::class.java)
            context.startActivity(refresh)
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier.padding(horizontal = DIMENS_16dp, vertical = DIMENS_8dp)
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            actions = {
                IconButton(onClick = {
                    onChangeView.invoke()
                    changeView = !changeView
                }) {
                    Icon(
                        painter = if (changeView) painterResource(id = R.drawable.icon_grid) else painterResource(id = R.drawable.icon_horizontal_grid),
                        contentDescription = "Grid Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(
                            DIMENS_20dp
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.face_1), // Replace with your drawable resource
                    contentDescription = "profile img",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(DIMENS_1dp, Color.Gray, CircleShape)
                        .clickable {
                            navController.navigate("chat_screen") // Navigate to the chat screen on click
                        }
                )
            },
            elevation = 0.dp, // Removed elevation to avoid shadow overlay
            title = {
                Text(
                    text = "Search your notes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    textAlign = TextAlign.Start,
                    color = textOnWhite,
                    fontWeight = FontWeight.Light,
                    fontSize = TEXT_SIZE_14sp
                )
            },
            modifier = Modifier
                .background(
                    color = Color(0xFFE2E9FC),
                    shape = RoundedCornerShape(DIMENS_50dp)
                )
                .padding(horizontal = DIMENS_8dp)
                .clip(RoundedCornerShape(DIMENS_50dp)) // Clip the TopAppBar to ensure rounded corners
                .clickable {
                    onSearch.invoke()
                },
            navigationIcon = {
                IconButton(onClick = { onClickAction.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu icon",
                        tint = Color.Black
                    )
                }
            }
        )
    }

    if (isLoading) {
        ProgressIndicator()
    }
}
