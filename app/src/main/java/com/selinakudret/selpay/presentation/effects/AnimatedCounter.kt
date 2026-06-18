package com.selinakudret.selpay.presentation.effects

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedCounter(
    value: Float,
    prefix: String = "",
    suffix: String = "",
    fontSize: TextUnit = 36.sp,
    color: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Black,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(dampingRatio = 0.85f, stiffness = 120f),
        label = "counter"
    )

    Text(
        text = "$prefix${"%,.2f".format(animatedValue)}$suffix",
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}