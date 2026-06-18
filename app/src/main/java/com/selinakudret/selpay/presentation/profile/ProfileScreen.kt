package com.selinakudret.selpay.presentation.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.ui.theme.*

@Composable
fun ProfileScreen(navController: NavController) {
    val haptic = rememberHaptic()

    var name by remember { mutableStateOf("Selina Kudret") }
    var email by remember { mutableStateOf("kudretselina7@gmail.com") }
    var phone by remember { mutableStateOf("+90 544 580 1031") }
    var address by remember { mutableStateOf("Nicosia, Cyprus") }

    val infinite = rememberInfiniteTransition(label = "verified")
    val pulse by infinite.animateFloat(
        initialValue = 1f, targetValue = 1.04f,
        animationSpec = infiniteRepeatable(tween(1800), RepeatMode.Reverse),
        label = "pulse"
    )

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
            Text("My Profile", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Hero
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue)))
                        .border(3.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("SK", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black)
                }
                Spacer(Modifier.height(14.dp))
                Text(name, color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(2.dp))
                Text("Customer ID: 1234567890", color = TextSecondary, fontSize = 12.sp)
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .scale(pulse)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(GreenPositive.copy(alpha = 0.2f), AccentTeal.copy(alpha = 0.2f))
                            )
                        )
                        .border(0.5.dp, GreenPositive.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(16.dp).clip(CircleShape).background(GreenPositive),
                        contentAlignment = Alignment.Center
                    ) { Text("✓", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black) }
                    Spacer(Modifier.width(6.dp))
                    Text("Verified Account", color = GreenPositive, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(28.dp))

            // Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .border(0.5.dp, GlassBorder, RoundedCornerShape(20.dp))
                    .padding(vertical = 18.dp)
            ) {
                StatItem("47", "Transactions", Modifier.weight(1f))
                StatDivider()
                StatItem("4", "Cards", Modifier.weight(1f))
                StatDivider()
                StatItem("2", "Goals", Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))

            Text("PERSONAL INFORMATION", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(10.dp))

            EditableCard {
                EditableRow("Full Name", name, hint = "Tap to edit") {
                    haptic.trigger(HapticType.SELECTION)
                    name = it
                }
                InfoDivider()
                StaticRow("Date of Birth", "12 March 2002")
                InfoDivider()
                StaticRow("Nationality", "Turkish")
                InfoDivider()
                StaticRow("Civil ID", "•••• •••• 4821")
                InfoDivider()
                EditableRow("Email", email, hint = "Tap to edit") {
                    haptic.trigger(HapticType.SELECTION)
                    email = it
                }
                InfoDivider()
                EditableRow("Phone", phone, hint = "Tap to edit") {
                    haptic.trigger(HapticType.SELECTION)
                    phone = it
                }
                InfoDivider()
                EditableRow("Address", address, hint = "Tap to edit") {
                    haptic.trigger(HapticType.SELECTION)
                    address = it
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 22.sp)
        Spacer(Modifier.height(2.dp))
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}

@Composable
private fun StatDivider() {
    Box(modifier = Modifier.width(0.5.dp).height(36.dp).background(GlassBorder))
}

@Composable
private fun EditableCard(content: @Composable () -> Unit) {
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
private fun EditableRow(label: String, value: String, hint: String, onChange: (String) -> Unit) {
    var editing by remember { mutableStateOf(false) }
    var localValue by remember { mutableStateOf(value) }
    val focus = remember { FocusRequester() }

    LaunchedEffect(editing) {
        if (editing) focus.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!editing) editing = true
            }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
        if (editing) {
            BasicTextField(
                value = localValue,
                onValueChange = { localValue = it },
                textStyle = TextStyle(
                    color = Primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                ),
                cursorBrush = SolidColor(Primary),
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focus)
            )
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Primary)
                    .clickable {
                        onChange(localValue)
                        editing = false
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Save", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun StaticRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun InfoDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(0.5.dp)
            .background(GlassBorder)
    )
}