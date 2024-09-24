package com.example.keepnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkPrimaryColor,
    primaryVariant = DarkPrimaryColor,
    secondary = DarkSecondaryColor,
    background = DarkBackground,
    surface = DarkSearchBarColor, // Use the DarkSearchBarColor for the surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = LightPrimaryColor,
    primaryVariant = LightPrimaryColor,
    secondary = LightSecondaryColor,
    background = LightBackground,
    surface = LightSearchBarColor, // Use the LightSearchBarColor for the surface
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun KeepNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setNavigationBarColor(
            color = DarkBackground
        )
        systemUiController.setSystemBarsColor(
            color = DarkBackground
        )
        DarkColorPalette
    } else {
        systemUiController.setNavigationBarColor(
            color = LightBackground
        )
        systemUiController.setSystemBarsColor(
            color = LightBackground
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = TypographyStyle,
        shapes = Shapes,
        content = content,
    )
}
