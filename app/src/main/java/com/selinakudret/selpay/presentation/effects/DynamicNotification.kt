package com.selinakudret.selpay.presentation.effects

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.selinakudret.selpay.ui.theme.*
import kotlinx.coroutines.delay

data class DynamicNotification(
    val icon: String,
    val title: String,
    val subtitle: String,
    val accentColor: Color = Primary
)

@Composable
fun DynamicNotificationOverlay(
    notification: DynamicNotification?,
    onDismiss: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(notification) {
        if (notification != null) {
            visible = true
            delay(3500)
            visible = false
            delay(400)
            onDismiss()
        }
    }

    if (notification != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = expandIn(
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = 380f)
                ) + fadeIn(),
                exit = shrinkOut(
                    animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f)
                ) + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.Black)
                        .border(1.dp, notification.accentColor.copy(alpha = 0.4f), RoundedCornerShape(28.dp))
                        .clickable { visible = false }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp).clip(CircleShape)
                                .background(notification.accentColor.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                notification.icon,
                                color = notification.accentColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                notification.title,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                notification.subtitle,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}