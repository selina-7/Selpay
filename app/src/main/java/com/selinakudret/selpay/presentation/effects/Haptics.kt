package com.selinakudret.selpay.presentation.effects

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

enum class HapticType { LIGHT, MEDIUM, HEAVY, SUCCESS, WARNING, SELECTION }

class HapticHelper(private val view: View, private val context: Context) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    fun trigger(type: HapticType) {
        try {
            when (type) {
                HapticType.LIGHT -> view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                HapticType.MEDIUM -> view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                HapticType.HEAVY -> view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                HapticType.SUCCESS -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator?.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                    }
                }
                HapticType.WARNING -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator?.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 50, 50, 50), -1))
                    }
                }
                HapticType.SELECTION -> view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            }
        } catch (_: Exception) { }
    }
}

@Composable
fun rememberHaptic(): HapticHelper {
    val view = LocalView.current
    val context = LocalContext.current
    return remember { HapticHelper(view, context) }
}