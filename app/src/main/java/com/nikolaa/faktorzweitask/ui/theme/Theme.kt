package com.nikolaa.faktorzweitask.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = blackColor,
    secondary = darkGrayColor,
    background = blackColor,
    surface = darkGrayColor,
    onPrimary = whiteColor,
    onSecondary = lightGrayColor,
    onBackground = whiteColor,
    onSurface = whiteColor,
)

private val LightColorScheme = lightColorScheme(
    primary = whiteColor,
    secondary = lightGrayColor,
    background = whiteColor,
    surface = lightGrayColor,
    onPrimary = blackColor,
    onSecondary = darkGrayColor,
    onBackground = blackColor,
    onSurface = blackColor,
)

@Composable
fun FaktorZweiTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}