package com.instance.dataxbranch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
        primary = primarydark,
        primaryVariant = primaryVariantdark,
        secondary = secondarydark
)

private val LightColorPalette = lightColors(
    primary = primarylight,
    primaryVariant = primaryVariantlight,
    secondary = secondarylight

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
val RainbowColors = listOf(Color.Cyan, Color.Blue, purple400, Color.Red, Color.Yellow)
@Composable
fun DataXBranchTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
    )
}