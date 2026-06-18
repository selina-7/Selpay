package com.selinakudret.selpay.presentation.effects

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    val startX: Float,
    val startY: Float,
    val angle: Float,
    val speed: Float,
    val color: Color,
    val size: Float,
    val rotationSpeed: Float
)

@Composable
fun ConfettiOverlay(trigger: Int) {
    if (trigger == 0) return

    val particles = remember(trigger) {
        List(60) {
            Particle(
                startX = 0.5f,
                startY = 0.5f,
                angle = Random.nextFloat() * 360f,
                speed = Random.nextFloat() * 800f + 400f,
                color = listOf(
                    Color(0xFFFFD700), Color(0xFFFF6B6B), Color(0xFF4ECDC4),
                    Color(0xFF95E1D3), Color(0xFFFCE38A), Color(0xFFF38181),
                    Color(0xFFAA96DA), Color(0xFFFFEAA7)
                ).random(),
                size = Random.nextFloat() * 8f + 4f,
                rotationSpeed = Random.nextFloat() * 720f - 360f
            )
        }
    }

    var progress by remember(trigger) { mutableStateOf(0f) }

    LaunchedEffect(trigger) {
        androidx.compose.animation.core.Animatable(0f).animateTo(
            targetValue = 1f,
            animationSpec = tween(2500, easing = LinearEasing)
        ) { progress = value }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { p ->
            val rad = Math.toRadians(p.angle.toDouble()).toFloat()
            val x = size.width * p.startX + cos(rad) * p.speed * progress
            val y = size.height * p.startY + sin(rad) * p.speed * progress + 800f * progress * progress
            val alpha = 1f - progress

            if (alpha > 0) {
                drawCircle(
                    color = p.color.copy(alpha = alpha),
                    radius = p.size,
                    center = Offset(x, y)
                )
            }
        }
    }
}