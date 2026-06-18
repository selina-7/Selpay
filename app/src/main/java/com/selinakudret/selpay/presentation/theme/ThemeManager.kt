package com.selinakudret.selpay.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

val LocalThemeState = compositionLocalOf<MutableState<Boolean>> {
    error("ThemeState not provided")
}

@Composable
fun ThemeProvider(content: @Composable () -> Unit) {
    val darkMode = remember { mutableStateOf(true) }
    CompositionLocalProvider(LocalThemeState provides darkMode) {
        content()
    }
}