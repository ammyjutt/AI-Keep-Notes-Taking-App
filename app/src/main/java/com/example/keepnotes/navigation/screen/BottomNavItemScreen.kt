package com.example.keepnotes.navigation.screen

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.keepnotes.R

sealed class BottomNavItemScreen(val route: String, val icon: Int, val title: String) {

    data object Home : BottomNavItemScreen("home_screen", R.drawable.notes_icon, "")
    data object Search : BottomNavItemScreen("search_screen", com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark, "")
    data object EditNote : BottomNavItemScreen("edit_note_screen", R.drawable.notes_icon, "")

    data object CheckListNote : BottomNavItemScreen("check_list_note_screen", R.drawable.notes_icon, "")

    data object DrawNote : BottomNavItemScreen("draw_note_screen", R.drawable.notes_icon, "")

    data object VoiceNote : BottomNavItemScreen("voice_note_screen", R.drawable.notes_icon, "")
    data object PictureNote : BottomNavItemScreen("picture_note_screen", R.drawable.notes_icon, "")

}