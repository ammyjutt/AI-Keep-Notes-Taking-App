package com.example.keepnotes.presentation.screen.authscreen



import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.keepnotes.R
import com.example.keepnotes.ui.theme.DIMENS_8dp
import com.example.keepnotes.ui.theme.TEXT_SIZE_24sp


@Composable
fun MyLoginScreen(
    navController: NavHostController,
    loginViewModel: AuthorizationViewModel = hiltViewModel()
) {
    val isImeVisible by rememberImeState()

    // State for username and password
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State for showing login error
    var loginError by remember { mutableStateOf(false) }

    GradientBox(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val animatedUpperSectionRatio by animateFloatAsState(
                targetValue = if (isImeVisible) 0f else 0.35f,
                label = "",
            )
            AnimatedVisibility(visible = !isImeVisible, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedUpperSectionRatio),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.notes_icon), // Replace with your logo drawable resource
                        contentDescription = "Logo",
                        colorFilter = ColorFilter.tint(Color.White), // Apply the tint using ColorFilter
                        modifier = Modifier
                            .size(80.dp) // Adjust the size as needed
                            .padding(bottom = 8.dp , start = 2.dp) // Space between the icon and the text

                    )

                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }




                Image(
                    painter = painterResource(R.drawable.glitter_icon), // Replace with your logo drawable resource
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp) // Adjust the size as needed
                        .padding(end = DIMENS_8dp) // Space between the logo and the text
                )

                androidx.compose.material.Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4E5053), // Color for "AI"
                                fontWeight = FontWeight.Bold,
                                fontSize = TEXT_SIZE_24sp
                            )
                        ) {
                            append("AI ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF9AA0A6), // Color for "Keep"
                                fontWeight = FontWeight.Normal,
                                fontSize = TEXT_SIZE_24sp
                            )
                        ) {
                            append("Keep")
                        }
                    },
                    modifier = Modifier
                        .padding(start = DIMENS_8dp) // Adjust padding if needed
                )







                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }

                // Username TextField
                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Username",
                    text = username,  // Passing the username state
                    onTextChange = { username = it },  // Updating the username state
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions()
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Password TextField
                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Password",
                    text = password,  // Passing the password state
                    onTextChange = { password = it },  // Updating the password state
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions(),
                    trailingIcon = Icons.Default.Lock
                )

                // Show login error if login fails
                if (loginError) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Login failed. Please try again.", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Log In and Sign Up buttons, always visible regardless of keyboard
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween, // Ensure the buttons are side by side
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Log In Button with reduced width
                        Button(
                            onClick = {
                                // Pass the entered username and password to the login function
                                loginViewModel.login(username, password) { success ->
                                    if (success) {
                                        // Navigate to the main screen if login is successful
                                        navController.navigate("MY_MAIN_SCREEN")
                                    } else {
                                        // Show login error if login failed
                                        loginError = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2C3034),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Log In",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))
                            )
                        }


                        // Sign Up Button with white background, black border
                        Button(
                            onClick = {
                                navController.navigate("my_signup_screen")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.Black) // Black border for the Sign Up button
                        ) {
                            Text(
                                text = "Sign Up",
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))
                            )
                        }
                    }
                }
            }
        }
    }
}











