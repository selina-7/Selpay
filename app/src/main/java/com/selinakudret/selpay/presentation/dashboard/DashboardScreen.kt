package com.selinakudret.selpay.presentation.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.AnimatedCounter
import com.selinakudret.selpay.presentation.effects.AnimatedGradientMesh
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.presentation.theme.LocalHalalMode
import com.selinakudret.selpay.ui.theme.*
import java.util.Calendar

@Composable
fun DashboardScreen(navController: NavController) {
    var displayBalance by remember { mutableStateOf(0f) }
    var balanceHidden by remember { mutableStateOf(false) }
    val halalMode by LocalHalalMode.current
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()
    LaunchedEffect(Unit) { displayBalance = 4250f }

    val halalGreen = Color(0xFF14B886)
    val greeting = getSmartGreeting()

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        AnimatedGradientMesh(
            colors = listOf(AccentPurple, AccentBlue, AccentTeal),
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 120.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .clickable {
                            haptic.trigger(HapticType.LIGHT)
                            navController.navigate("profile")
                        }
                        .padding(end = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("SK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(greeting, fontSize = 11.sp, color = TextSecondary)
                        Text("Selina", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                }

                Row {
                    Box {
                        IconBox("◇") {
                            haptic.trigger(HapticType.LIGHT)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(AccentRose)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    IconBox("⚙") {
                        haptic.trigger(HapticType.LIGHT)
                        navController.navigate("settings")
                    }
                }
            }

            // Halal banner
            if (halalMode) {
                Spacer(Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(halalGreen.copy(alpha = 0.12f))
                        .border(0.5.dp, halalGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                        .clickable {
                            haptic.trigger(HapticType.LIGHT)
                            navController.navigate("zakat")
                        }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("☪", color = halalGreen, fontSize = 14.sp)
                        Spacer(Modifier.width(8.dp))
                        Text("Halal Mode", color = halalGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        Text("Zakat in 87 days  ›", color = halalGreen.copy(alpha = 0.8f), fontSize = 11.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Hero balance
            HeroBalance(
                balance = displayBalance,
                currency = currency.code,
                hidden = balanceHidden,
                onToggleHidden = {
                    haptic.trigger(HapticType.SELECTION)
                    balanceHidden = !balanceHidden
                }
            )

            Spacer(Modifier.height(20.dp))

            // Quick actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LargeAction("↑", "Send", AccentPurple, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.MEDIUM)
                    navController.navigate("send")
                }
                LargeAction("↓", "Receive", AccentTeal, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.MEDIUM)
                    navController.navigate("receive")
                }
                LargeAction("⇄", "Swap", AccentBlue, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.MEDIUM)
                    navController.navigate("swap")
                }
                LargeAction("◫", "Scan", AccentGold, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.MEDIUM)
                    navController.navigate("scan")
                }
            }

            Spacer(Modifier.height(28.dp))

            // Goal section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Closest goal", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(
                    "All goals  ›",
                    color = Primary, fontSize = 12.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { haptic.trigger(HapticType.LIGHT) }
                )
            }
            Spacer(Modifier.height(10.dp))
            SingleGoalCard(
                title = "New iPhone",
                icon = "▣",
                current = 320f,
                target = 600f,
                colors = listOf(AccentTeal, AccentBlue),
                currency = currency.code
            )

            Spacer(Modifier.height(28.dp))

            // Transactions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(
                    "View all  ›",
                    color = Primary, fontSize = 12.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        haptic.trigger(HapticType.LIGHT)
                        navController.navigate("transactions")
                    }
                )
            }
            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
            ) {
                Column {
                    CleanTxRow("Salary", "Today", "+850.00", true, currency.code)
                    TxDiv()
                    CleanTxRow("Netflix", "Today", "-3.50", false, currency.code)
                    TxDiv()
                    CleanTxRow("Carrefour", "Yesterday", "-22.10", false, currency.code)
                }
            }
        }
    }
}

@Composable
private fun HeroBalance(
    balance: Float,
    currency: String,
    hidden: Boolean,
    onToggleHidden: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.linearGradient(listOf(GradientStart, GradientMid, GradientEnd)))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 50.dp, y = (-50).dp)
                .size(180.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .size(130.dp)
                .background(Color.White.copy(alpha = 0.08f), CircleShape)
        )

        Column(modifier = Modifier.padding(28.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Balance", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable(onClick = onToggleHidden)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(if (hidden) "Show" else "Hide", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(12.dp))
            if (hidden) {
                Text(
                    "$currency ••••••",
                    fontSize = 40.sp, fontWeight = FontWeight.Black, color = Color.White
                )
            } else {
                AnimatedCounter(
                    value = balance,
                    prefix = "$currency ",
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("▴ 2.4%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(8.dp))
                Text("vs last month", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun LargeAction(
    icon: String,
    label: String,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .border(0.5.dp, accent.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .springClickable()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(38.dp).clip(CircleShape)
                .background(accent.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 18.sp, color = accent, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(8.dp))
        Text(label, fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SingleGoalCard(
    title: String,
    icon: String,
    current: Float,
    target: Float,
    colors: List<Color>,
    currency: String
) {
    val progress = current / target
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.linearGradient(colors))
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp).clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) { Text(icon, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(
                            "$currency ${current.toInt()} of ${target.toInt()}",
                            color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp
                        )
                    }
                }
                Text(
                    "${(progress * 100).toInt()}%",
                    color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(14.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth().height(6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.25f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
private fun CleanTxRow(
    title: String,
    time: String,
    amount: String,
    positive: Boolean,
    currency: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp).clip(CircleShape)
                .background(if (positive) GreenPositive.copy(alpha = 0.15f) else CardElevated),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (positive) "↓" else "↑",
                color = if (positive) GreenPositive else TextSecondary,
                fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(time, color = TextSecondary, fontSize = 11.sp)
        }
        Text(
            "${if (positive) "+" else "-"}$currency ${amount.removePrefix("-").removePrefix("+")}",
            color = if (positive) GreenPositive else TextPrimary,
            fontWeight = FontWeight.Bold, fontSize = 14.sp
        )
    }
}

@Composable
private fun TxDiv() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .height(0.5.dp)
            .background(GlassBorder)
    )
}

@Composable
fun IconBox(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(CardDark)
            .border(0.5.dp, GlassBorder, CircleShape)
            .springClickable()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(symbol, color = TextPrimary, fontSize = 15.sp)
    }
}

@Composable
private fun getSmartGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..21 -> "Good evening"
        else -> "Good night"
    }
}