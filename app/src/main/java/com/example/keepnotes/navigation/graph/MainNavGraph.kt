package com.example.keepnotes.navigation.graph

import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.keepnotes.navigation.screen.BottomNavItemScreen
import com.example.keepnotes.navigation.screen.Screen
import com.example.keepnotes.presentation.screen.ChatViewModel.ChatScreen
import com.example.keepnotes.presentation.screen.allnotes.AllNotesScreen

import com.example.keepnotes.presentation.screen.editnote.EditNoteScreen
import com.example.keepnotes.presentation.screen.home.RootScreen
import com.example.keepnotes.presentation.screen.picturenote.PictureNote
import com.example.keepnotes.presentation.screen.search.SearchNotesScreen
import com.example.keepnotes.presentation.screen.voicenote.VoiceNote
import com.example.keepnotes.utils.Constants.NOTE_ARGUMENT_KEY



@Composable
fun MainNavGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        route = Graph.MAIN,
        startDestination = BottomNavItemScreen.Home.route
    ) {
        composable(route = BottomNavItemScreen.Home.route) {
            RootScreen(navController = navHostController)
        }
        composable(route = BottomNavItemScreen.Search.route) {
            SearchNotesScreen(navController = navHostController)
        }



        composable(route = BottomNavItemScreen.CheckListNote.route) {
//            CheckListNote()
        }
        composable(route = BottomNavItemScreen.DrawNote.route) {
//            DrawNote()
        }
        composable(route = BottomNavItemScreen.VoiceNote.route) {
            VoiceNote()
        }
        composable(route = BottomNavItemScreen.PictureNote.route) {
            PictureNote()
        }




        composable(route = "chat_screen") {
            ChatScreen()
        }


        composable(route = "settings_screen") {
            SettingsScreen()
        }


        // Define a composable for AllNotesScreen with a category parameter
        composable(
            route = "all_notes_screen/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")?.lowercase()
            
            AllNotesScreen(
                navController = navHostController,
                openDrawer = { /* your openDrawer action */ },
                category = category
            )
        }



        detailsNavGraph(navHostController = navHostController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.EDITNOTE,
        startDestination = Screen.EditNote.route
    ) {
        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument(NOTE_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString(NOTE_ARGUMENT_KEY, "-1")
            if (noteId != null) {
                EditNoteScreen(navController = navHostController,noteId = noteId)
            }
        }
    }
}