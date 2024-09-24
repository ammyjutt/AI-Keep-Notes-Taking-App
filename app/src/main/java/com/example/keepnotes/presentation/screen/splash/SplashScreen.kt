package com.example.keepnotes.presentation.screen.splash

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.keepnotes.R
import com.example.keepnotes.navigation.graph.Graph
import com.example.keepnotes.navigation.screen.Screen
import kotlinx.coroutines.delay

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {


    val onBoardingIsCompleted by splashViewModel.onBoardingIsCompleted.collectAsState()

    val scale = remember { Animatable(2f) }

    LaunchedEffect(key1 = true, ){
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(1600L)
        navController.popBackStack()

        if (onBoardingIsCompleted) {
            Log.d("onBoardingIsCompleted", "--------------------------------------")

            navController.navigate("my_login_screen")
        }

        else {
//            navController.navigate(Screen.OnBoarding.route)
        }

    }
    Splash(scale = scale.value)
}

@Composable
fun Splash(
    modifier: Modifier = Modifier,
    scale: Float
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .fillMaxSize()
            .scale(scale), // Apply scaling to the entire column
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.glitter_icon), // Replace with your logo drawable resource
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp) // Adjust the size as needed
                .padding(bottom = 8.dp) // Space between the icon and the text
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color(0xFF4E5053), // Color for "AI"
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                ) {
                    append("AI ")
                }
                withStyle(style = SpanStyle(
                    color = Color(0xFF9AA0A6), // Color for "Keep"
                    fontWeight = FontWeight.Normal,
                    fontSize = 40.sp
                )) {
                    append("Keep")
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp) // Adjust padding if needed
        )
    }
}
