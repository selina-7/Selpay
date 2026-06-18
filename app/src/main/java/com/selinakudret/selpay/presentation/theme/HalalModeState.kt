package com.selinakudret.selpay.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

val LocalHalalMode = compositionLocalOf<MutableState<Boolean>> {
    error("HalalMode not provided")
}

@Composable
fun HalalModeProvider(content: @Composable () -> Unit) {
    val halalMode = remember { mutableStateOf(false) }
    CompositionLocalProvider(LocalHalalMode provides halalMode) {
        content()
    }
}