package com.selinakudret.selpay.presentation.effects

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedGradientMesh(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val infinite = rememberInfiniteTransition(label = "mesh")
    val t by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing)),
        label = "t"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        colors.forEachIndexed { i, color ->
            val phase = i * 0.785f
            val cx = w * (0.5f + 0.3f * cos(t * 2 * Math.PI.toFloat() + phase))
            val cy = h * (0.5f + 0.3f * sin(t * 2 * Math.PI.toFloat() + phase * 1.3f))
            val radius = w * 0.6f

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.25f), Color.Transparent),
                    center = Offset(cx, cy),
                    radius = radius
                ),
                center = Offset(cx, cy),
                radius = radius
            )
        }
    }
}