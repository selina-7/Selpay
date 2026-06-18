package com.selinakudret.selpay.presentation.effects

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class GoldParticle(
    val startX: Float,
    val angle: Float,
    val speed: Float,
    val size: Float,
    val delay: Float
)

@Composable
fun GoldParticleBurst(trigger: Int) {
    if (trigger == 0) return

    val particles = remember(trigger) {
        List(40) {
            GoldParticle(
                startX = Random.nextFloat(),
                angle = -90f + Random.nextFloat() * 40f - 20f,
                speed = Random.nextFloat() * 400f + 300f,
                size = Random.nextFloat() * 6f + 3f,
                delay = Random.nextFloat() * 0.3f
            )
        }
    }

    var progress by remember(trigger) { mutableStateOf(0f) }
    LaunchedEffect(trigger) {
        androidx.compose.animation.core.Animatable(0f).animateTo(
            targetValue = 1f,
            animationSpec = tween(1800, easing = LinearEasing)
        ) { progress = value }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { p ->
            val localProgress = ((progress - p.delay) / (1f - p.delay)).coerceIn(0f, 1f)
            if (localProgress > 0) {
                val rad = (p.angle * PI / 180f).toFloat()
                val x = size.width * p.startX + cos(rad) * p.speed * localProgress
                val y = size.height * 0.7f + sin(rad) * p.speed * localProgress
                val alpha = 1f - localProgress

                drawCircle(
                    color = Color(0xFFFFD700).copy(alpha = alpha),
                    radius = p.size,
                    center = Offset(x, y)
                )
                drawCircle(
                    color = Color(0xFFFFA500).copy(alpha = alpha * 0.5f),
                    radius = p.size * 1.5f,
                    center = Offset(x, y)
                )
            }
        }
    }
}