package com.wakaztahir.common.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
//    primary = blue500,
//    primaryVariant = blue700,
//    onPrimary = Color.White,
//    secondary = teal200
)

private val LightColorPalette = lightColorScheme(
//    primary = blue500,
//    primaryVariant = blue700,
//    onPrimary = Color.White,
//    secondary = teal200
)

@Composable
internal fun ComposeMaterialDialogsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
