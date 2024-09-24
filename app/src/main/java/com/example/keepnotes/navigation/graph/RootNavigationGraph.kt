package com.example.keepnotes.navigation.graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.keepnotes.data.local.InMemoryCache
import com.example.keepnotes.domain.model.User
import com.example.keepnotes.navigation.screen.Screen
import com.example.keepnotes.presentation.common.ProgressIndicator
import com.example.keepnotes.presentation.screen.authscreen.MyLoginScreen
import com.example.keepnotes.presentation.screen.authscreen.MySignUpScreen
import com.example.keepnotes.presentation.screen.loginscreen.LoginScreen
import com.example.keepnotes.presentation.screen.splash.SplashScreen


@Composable
fun RootNavigationGraph(navHostController: NavHostController) {


    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navHostController)
        }

        composable(route = "MY_MAIN_SCREEN") {
            var isLoading by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = true) {
                navHostController.navigate(Graph.MAIN)
            }

            if (isLoading) {
                ProgressIndicator()
            }
        }

        composable(route = Graph.MAIN) {
            MainNavGraph()
        }

        composable("my_login_screen")
        {
            MyLoginScreen(navController = navHostController)
        }
        
        composable(route = "my_signup_screen")
        {
            MySignUpScreen(navController = navHostController)
        }
    }
}
