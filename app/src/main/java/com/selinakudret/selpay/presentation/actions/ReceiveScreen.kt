package com.selinakudret.selpay.presentation.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.ui.theme.*

@Composable
fun ReceiveScreen(navController: NavController) {
    val haptic = rememberHaptic()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp).clip(CircleShape).background(CardDark)
                    .springClickable()
                    .clickable {
                        haptic.trigger(HapticType.LIGHT)
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) { Text("‹", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(16.dp))
            Text("Receive Money", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Share your QR code", color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Anyone can scan to send you money", color = TextSecondary, fontSize = 12.sp)

            Spacer(Modifier.height(32.dp))

            // QR with gradient border
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.White)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QrPlaceholder()
                }
            }

            Spacer(Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .border(0.5.dp, GlassBorder, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text("ACCOUNT HOLDER", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("Selina Kudret", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(14.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(GlassBorder))
                    Spacer(Modifier.height(14.dp))
                    Text("IBAN", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("KW81 SELP 0000 0000 0123 4567 89", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActBtn("Copy", Modifier.weight(1f)) { haptic.trigger(HapticType.SUCCESS) }
                ActBtn("Share", Modifier.weight(1f)) { haptic.trigger(HapticType.LIGHT) }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun QrPlaceholder() {
    Column {
        repeat(10) { row ->
            Row {
                repeat(10) { col ->
                    val filled = ((row * 7 + col * 3 + 11) % 5) != 0 ||
                            (row in 0..1 && col in 0..1) ||
                            (row in 0..1 && col in 8..9) ||
                            (row in 8..9 && col in 0..1)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(if (filled) Color.Black else Color.White)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActBtn(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(1.dp, Primary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .springClickable()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}