package com.selinakudret.selpay.presentation.cards

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.ui.theme.*

@Composable
fun CardDetailsScreen(navController: NavController) {
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()
    var showFullNumber by remember { mutableStateOf(false) }
    var showCvv by remember { mutableStateOf(false) }

    // Card tilt animation
    val infinite = rememberInfiniteTransition(label = "tilt")
    val tiltRotation by infinite.animateFloat(
        initialValue = -2f, targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(4000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "tilt"
    )

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero gradient header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(Brush.linearGradient(listOf(GradientStart, GradientMid, GradientEnd)))
            ) {
                // Decorative orbs
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 80.dp, y = (-80).dp)
                        .size(280.dp)
                        .background(Color.White.copy(alpha = 0.08f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = (-60).dp, y = 60.dp)
                        .size(220.dp)
                        .background(Color.White.copy(alpha = 0.06f), CircleShape)
                )

                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp).clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .springClickable()
                            .clickable {
                                haptic.trigger(HapticType.LIGHT)
                                navController.popBackStack()
                            },
                        contentAlignment = Alignment.Center
                    ) { Text("‹", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.width(14.dp))
                    Text(
                        "Card Details",
                        color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp
                    )
                }

                // Centered card with tilt
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 40.dp)
                        .rotate(tiltRotation)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Black.copy(alpha = 0.2f)
                                    )
                                )
                            )
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                            .padding(18.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("SelPay", color = Color.White, fontWeight = FontWeight.Black, fontSize = 17.sp)
                                Text("VISA", color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
                            }
                            Spacer(Modifier.weight(1f))
                            Text(
                                if (showFullNumber) "4821 5523 7745 1209" else "•••• •••• •••• 4821",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 2.sp
                            )
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("CARDHOLDER", color = Color.White.copy(alpha = 0.6f), fontSize = 8.sp)
                                    Text("SELINA KUDRET", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("EXPIRES", color = Color.White.copy(alpha = 0.6f), fontSize = 8.sp)
                                    Text("12/28", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp).padding(top = 20.dp)) {
                // Reveal button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RevealButton(
                        label = if (showFullNumber) "Hide number" else "Show number",
                        icon = if (showFullNumber) "◐" else "◉",
                        active = showFullNumber,
                        modifier = Modifier.weight(1f)
                    ) {
                        haptic.trigger(HapticType.SELECTION)
                        showFullNumber = !showFullNumber
                    }
                    RevealButton(
                        label = if (showCvv) "Hide CVV" else "Show CVV",
                        icon = if (showCvv) "◐" else "◉",
                        active = showCvv,
                        modifier = Modifier.weight(1f)
                    ) {
                        haptic.trigger(HapticType.SELECTION)
                        showCvv = !showCvv
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Card info
                Text("CARD INFORMATION", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(10.dp))

                DetailGroup {
                    DetailRow("Type", "Visa Platinum")
                    DetailDivider()
                    DetailRow("Number", if (showFullNumber) "4821 5523 7745 1209" else "•••• 4821")
                    DetailDivider()
                    DetailRow("Expires", "12/28")
                    DetailDivider()
                    DetailRow("CVV", if (showCvv) "823" else "•••")
                    DetailDivider()
                    DetailRow("Status", "Active", valueColor = GreenPositive)
                    DetailDivider()
                    DetailRow("Issued", "15 Mar 2024")
                }

                Spacer(Modifier.height(24.dp))

                Text("CARDHOLDER", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(10.dp))
                DetailGroup {
                    DetailRow("Name", "Selina Kudret")
                    DetailDivider()
                    DetailRow("Customer ID", "1234567890")
                    DetailDivider()
                    DetailRow("IBAN", "KW81 SELP 0000 ••••")
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun CardLimitsScreen(navController: NavController) {
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()
    var online by remember { mutableStateOf(true) }
    var international by remember { mutableStateOf(true) }
    var contactless by remember { mutableStateOf(true) }
    var atm by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
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
            Text("Card Limits", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text("SPENDING LIMITS", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(10.dp))

            LimitCard("Daily", "${currency.code} 500", "Resets at midnight", AccentTeal)
            Spacer(Modifier.height(10.dp))
            LimitCard("Monthly", "${currency.code} 5,000", "Resets 1st of month", AccentBlue)
            Spacer(Modifier.height(10.dp))
            LimitCard("ATM", "${currency.code} 300", "Daily withdrawal cap", AccentPurple)

            Spacer(Modifier.height(28.dp))

            Text("CARD PERMISSIONS", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(10.dp))

            PermissionToggle("Online Payments", "Web & app purchases", online) {
                haptic.trigger(HapticType.SELECTION)
                online = it
            }
            Spacer(Modifier.height(8.dp))
            PermissionToggle("International", "Outside Kuwait", international) {
                haptic.trigger(HapticType.SELECTION)
                international = it
            }
            Spacer(Modifier.height(8.dp))
            PermissionToggle("Contactless", "Tap to pay", contactless) {
                haptic.trigger(HapticType.SELECTION)
                contactless = it
            }
            Spacer(Modifier.height(8.dp))
            PermissionToggle("ATM Withdrawals", "Cash from ATMs", atm) {
                haptic.trigger(HapticType.SELECTION)
                atm = it
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun RevealButton(label: String, icon: String, active: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (active) Primary.copy(alpha = 0.18f) else CardDark)
            .border(0.5.dp, if (active) Primary else GlassBorder, RoundedCornerShape(14.dp))
            .springClickable()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, color = if (active) Primary else TextPrimary, fontSize = 14.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                label,
                color = if (active) Primary else TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LimitCard(title: String, value: String, sub: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(0.5.dp, color.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp).clip(CircleShape).background(color)
            )
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(2.dp))
                Text(value, color = color, fontWeight = FontWeight.Black, fontSize = 22.sp)
                Text(sub, color = TextMuted, fontSize = 10.sp)
            }
            Text("›", color = TextSecondary, fontSize = 18.sp)
        }
    }
}

@Composable
private fun PermissionToggle(title: String, sub: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(sub, color = TextSecondary, fontSize = 11.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = CardElevated
            )
        )
    }
}

@Composable
private fun DetailGroup(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(0.5.dp, GlassBorder, RoundedCornerShape(18.dp))
    ) {
        Column { content() }
    }
}

@Composable
private fun DetailRow(label: String, value: String, valueColor: Color? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text(
            value,
            color = valueColor ?: TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(0.5.dp)
            .background(GlassBorder)
    )
}