package com.example.keepnotes.presentation.screen.voicenote

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun VoiceNote(){
    Box(modifier = Modifier) {
        Text(text = "VoiceNote", color = Color.Green)
    }
}