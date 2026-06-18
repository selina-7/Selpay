package com.selinakudret.selpay.presentation.actions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.AnimatedCounter
import com.selinakudret.selpay.presentation.effects.ConfettiOverlay
import com.selinakudret.selpay.presentation.effects.GoldParticleBurst
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.ui.theme.*
import kotlinx.coroutines.delay

private data class Contact(val name: String, val initials: String, val gradient: List<Color>)

@Composable
fun SendScreen(navController: NavController) {
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()

    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }
    var sendTrigger by remember { mutableStateOf(0) }

    val contacts = remember {
        listOf(
            Contact("Ali", "AB", listOf(AccentPurple, AccentBlue)),
            Contact("Maria", "MJ", listOf(AccentRose, AccentGold)),
            Contact("Yusuf", "YK", listOf(AccentTeal, AccentBlue)),
            Contact("Sarah", "SO", listOf(AccentPurple, AccentRose)),
            Contact("Omar", "OM", listOf(AccentGold, AccentRose)),
            Contact("Layla", "LA", listOf(AccentTeal, NeonCyan))
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
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
                Column {
                    Text("Send Money", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
                    Text("Available: ${currency.code} 4,250.00", color = TextSecondary, fontSize = 11.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {
                // ============== AMOUNT DISPLAY ==============
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            currency.code,
                            color = TextSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        AnimatedCounter(
                            value = (amount.toFloatOrNull() ?: 0f),
                            fontSize = 56.sp,
                            color = if (amount.isEmpty()) TextMuted else TextPrimary,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(Modifier.height(8.dp))
                        if (selectedContact != null) {
                            Text(
                                "to ${selectedContact!!.name}",
                                color = TextSecondary,
                                fontSize = 13.sp
                            )
                        } else {
                            Text(
                                "Select recipient below",
                                color = TextMuted,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // ============== CONTACTS BUBBLES ==============
                Text("Recent contacts", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(contacts.size) { i ->
                        val c = contacts[i]
                        ContactBubble(
                            contact = c,
                            selected = selectedContact == c,
                            onClick = {
                                haptic.trigger(HapticType.SELECTION)
                                selectedContact = c
                            }
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                // ============== QUICK AMOUNT CHIPS ==============
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf("10", "25", "50", "100", "500").size) { i ->
                        val v = listOf("10", "25", "50", "100", "500")[i]
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(CardDark)
                                .border(0.5.dp, GlassBorder, RoundedCornerShape(14.dp))
                                .springClickable()
                                .clickable {
                                    haptic.trigger(HapticType.LIGHT)
                                    amount = v
                                }
                                .padding(horizontal = 18.dp, vertical = 10.dp)
                        ) {
                            Text(
                                "${currency.code} $v",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ============== CUSTOM NUMPAD ==============
                NumPad(
                    onDigit = { digit ->
                        haptic.trigger(HapticType.LIGHT)
                        if (digit == "." && amount.contains(".")) return@NumPad
                        if (amount.length < 8) amount += digit
                    },
                    onDelete = {
                        haptic.trigger(HapticType.LIGHT)
                        if (amount.isNotEmpty()) amount = amount.dropLast(1)
                    }
                )

                Spacer(Modifier.height(16.dp))

                // ============== SEND BUTTON ==============
                val canSend = amount.isNotEmpty() && (amount.toFloatOrNull() ?: 0f) > 0f && selectedContact != null
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            if (canSend) Brush.linearGradient(listOf(GradientStart, GradientEnd))
                            else Brush.linearGradient(listOf(CardDark, CardDark))
                        )
                        .clickable(enabled = canSend) {
                            haptic.trigger(HapticType.SUCCESS)
                            sendTrigger++
                            showSuccess = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (canSend) "Send ${currency.code} $amount" else "Enter amount",
                        color = if (canSend) Color.White else TextMuted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }

        // Gold particle burst when send is tapped
        GoldParticleBurst(trigger = sendTrigger)

        // Success modal overlay
        AnimatedVisibility(
            visible = showSuccess,
            enter = fadeIn(tween(300)) + scaleIn(tween(400)),
            exit = fadeOut(tween(200))
        ) {
            SuccessOverlay(
                amount = amount,
                recipient = selectedContact?.name ?: "",
                currency = currency.code,
                onDone = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun ContactBubble(
    contact: Contact,
    selected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "bubbleScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(62.dp).clip(CircleShape)
                .background(Brush.linearGradient(contact.gradient))
                .border(
                    if (selected) 3.dp else 0.dp,
                    if (selected) Color.White else Color.Transparent,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                contact.initials,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 17.sp
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            contact.name,
            color = if (selected) Primary else TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun NumPad(onDigit: (String) -> Unit, onDelete: () -> Unit) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "⌫")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { key ->
                    NumKey(
                        key = key,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (key == "⌫") onDelete()
                            else onDigit(key)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NumKey(key: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(54.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(0.5.dp, GlassBorder, RoundedCornerShape(16.dp))
            .springClickable(pressedScale = 0.92f)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            key,
            color = TextPrimary,
            fontSize = if (key == "⌫") 18.sp else 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============== SUCCESS OVERLAY ==============
@Composable
private fun SuccessOverlay(
    amount: String,
    recipient: String,
    currency: String,
    onDone: () -> Unit
) {
    var confettiTrigger by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        delay(200)
        confettiTrigger++
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark.copy(alpha = 0.95f)),
        contentAlignment = Alignment.Center
    ) {
        ConfettiOverlay(trigger = confettiTrigger)

        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Check mark with pulse
            val infinite = rememberInfiniteTransition(label = "successPulse")
            val pulseScale by infinite.animateFloat(
                initialValue = 1f, targetValue = 1.1f,
                animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
                label = "p"
            )
            Box(
                modifier = Modifier
                    .scale(pulseScale)
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(GreenPositive, AccentTeal))),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Black)
            }

            Spacer(Modifier.height(28.dp))
            Text(
                "Sent!",
                color = TextPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "$currency $amount to $recipient",
                color = TextSecondary,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .clickable(onClick = onDone),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Done",
                    color = BackgroundDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}