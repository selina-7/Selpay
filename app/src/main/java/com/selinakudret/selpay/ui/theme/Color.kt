package com.selinakudret.selpay.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.selinakudret.selpay.presentation.theme.LocalThemeState

// Premium gradients (theme-independent)
val GradientStart = Color(0xFF6366F1)
val GradientMid = Color(0xFF8B5CF6)
val GradientEnd = Color(0xFF06B6D4)

// Accents (theme-independent)
val AccentTeal = Color(0xFF14B8A6)
val AccentBlue = Color(0xFF3B82F6)
val AccentPurple = Color(0xFF8B5CF6)
val AccentGold = Color(0xFFEAB308)
val AccentRose = Color(0xFFF43F5E)

val Primary = Color(0xFF14B8A6)
val PrimaryDark = Color(0xFF0F766E)

// Status (theme-independent)
val GreenPositive = Color(0xFF22C55E)
val RedNegative = Color(0xFFEF4444)
val Warning = Color(0xFFEAB308)

// Crypto (theme-independent)
val BitcoinOrange = Color(0xFFF7931A)
val EthereumBlue = Color(0xFF627EEA)
val TetherGreen = Color(0xFF26A17B)
val BinanceYellow = Color(0xFFF0B90B)

// Legacy aliases
val NeonPurple = AccentPurple
val NeonCyan = Color(0xFF06B6D4)
val NeonMint = AccentTeal
val NeonPink = AccentRose
val NeonGold = AccentGold
val Accent = AccentPurple

// Dark theme palette
private val DarkBg = Color(0xFF0A0E1A)
private val DarkSurface = Color(0xFF111827)
private val DarkCard = Color(0xFF1A2032)
private val DarkCardEl = Color(0xFF232B40)
private val DarkText = Color(0xFFFFFFFF)
private val DarkTextSec = Color(0xFFA3AAB8)
private val DarkTextMuted = Color(0xFF6B7280)
private val DarkGlassBorder = Color(0x33FFFFFF)

// Light theme palette
private val LightBg = Color(0xFFF1F5F9)
private val LightSurface = Color(0xFFFFFFFF)
private val LightCard = Color(0xFFFFFFFF)
private val LightCardEl = Color(0xFFF8FAFC)
private val LightText = Color(0xFF0F172A)
private val LightTextSec = Color(0xFF64748B)
private val LightTextMuted = Color(0xFF94A3B8)
private val LightGlassBorder = Color(0x1A000000)

// Dynamic colors (change with theme)
val BackgroundDark: Color
    @Composable get() = if (LocalThemeState.current.value) DarkBg else LightBg

val SurfaceDark: Color
    @Composable get() = if (LocalThemeState.current.value) DarkSurface else LightSurface

val CardDark: Color
    @Composable get() = if (LocalThemeState.current.value) DarkCard else LightCard

val CardElevated: Color
    @Composable get() = if (LocalThemeState.current.value) DarkCardEl else LightCardEl

val TextPrimary: Color
    @Composable get() = if (LocalThemeState.current.value) DarkText else LightText

val TextSecondary: Color
    @Composable get() = if (LocalThemeState.current.value) DarkTextSec else LightTextSec

val TextMuted: Color
    @Composable get() = if (LocalThemeState.current.value) DarkTextMuted else LightTextMuted

val GlassBorder: Color
    @Composable get() = if (LocalThemeState.current.value) DarkGlassBorder else LightGlassBorder

// Light theme legacy (kept for compatibility)
val BackgroundLight = LightBg
val SurfaceLight = LightSurface
val CardLight = LightCard
val TextPrimaryLight = LightText
val TextSecondaryLight = LightTextSec

val GlassFill = Color(0x14FFFFFF)