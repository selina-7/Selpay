package com.selinakudret.selpay.presentation.actions

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.ui.theme.*

@Composable
fun ScanScreen(navController: NavController) {
    val infinite = rememberInfiniteTransition(label = "scan")
    val offset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Reverse),
        label = "off"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(title = "Scan to Pay", navController)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Scan frame
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .border(2.dp, Primary, RoundedCornerShape(28.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Scan line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(2.dp)
                            .offset(y = ((offset - 0.5f) * 200).dp)
                            .background(Primary)
                    )
                    // Corner brackets
                    listOf(
                        Alignment.TopStart, Alignment.TopEnd, Alignment.BottomStart, Alignment.BottomEnd
                    ).forEach { align ->
                        Box(
                            modifier = Modifier
                                .align(align)
                                .padding(12.dp)
                                .size(20.dp)
                                .border(3.dp, Primary, RoundedCornerShape(4.dp))
                        )
                    }
                }
                Spacer(Modifier.height(28.dp))
                Text("Point camera at a QR code", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("To send money or make a payment", color = TextSecondary, fontSize = 12.sp)
            }
        }

        // Bottom actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BottomAction("Upload QR", Modifier.weight(1f))
            BottomAction("My QR", Modifier.weight(1f)) { navController.navigate("receive") }
        }
    }
}

@Composable
private fun BottomAction(label: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .border(1.dp, Primary.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}