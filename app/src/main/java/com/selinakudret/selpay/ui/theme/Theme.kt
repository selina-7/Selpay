package com.selinakudret.selpay.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.selinakudret.selpay.presentation.theme.LocalThemeState

@Composable
fun SelPayTheme(content: @Composable () -> Unit) {
    val isDark by LocalThemeState.current

    val colors = if (isDark) {
        darkColorScheme(
            primary = Primary,
            secondary = AccentPurple,
            background = Color(0xFF0A0E1A),
            surface = Color(0xFF111827),
            onPrimary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Primary,
            secondary = AccentPurple,
            background = Color(0xFFF1F5F9),
            surface = Color(0xFFFFFFFF),
            onPrimary = Color.White,
            onBackground = Color(0xFF0F172A),
            onSurface = Color(0xFF0F172A),
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}