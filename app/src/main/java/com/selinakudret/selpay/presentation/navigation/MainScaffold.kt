package com.selinakudret.selpay.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.selinakudret.selpay.presentation.actions.ReceiveScreen
import com.selinakudret.selpay.presentation.actions.ScanScreen
import com.selinakudret.selpay.presentation.actions.SendScreen
import com.selinakudret.selpay.presentation.actions.SwapScreen
import com.selinakudret.selpay.presentation.assistant.AssistantScreen
import com.selinakudret.selpay.presentation.cards.CardDetailsScreen
import com.selinakudret.selpay.presentation.cards.CardLimitsScreen
import com.selinakudret.selpay.presentation.cards.CardsScreen
import com.selinakudret.selpay.presentation.crypto.BinanceConnectScreen
import com.selinakudret.selpay.presentation.crypto.CryptoScreen
import com.selinakudret.selpay.presentation.dashboard.DashboardScreen
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.family.FamilyScreen
import com.selinakudret.selpay.presentation.family.KidAccountScreen
import com.selinakudret.selpay.presentation.halal.SadaqaScreen
import com.selinakudret.selpay.presentation.halal.ZakatScreen
import com.selinakudret.selpay.presentation.insights.InsightsScreen
import com.selinakudret.selpay.presentation.profile.ProfileScreen
import com.selinakudret.selpay.presentation.settings.SettingsScreen
import com.selinakudret.selpay.presentation.transactions.TransactionsScreen
import com.selinakudret.selpay.ui.theme.*

@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Crypto,
        BottomNavItem.Cards,
        BottomNavItem.Insights
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            enterTransition = {
                fadeIn(animationSpec = tween(250)) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300),
                    initialOffset = { it / 8 }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(200))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(250))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(200)) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300),
                    targetOffset = { it / 8 }
                )
            }
        ) {
            composable(BottomNavItem.Home.route) { DashboardScreen(navController) }
            composable(BottomNavItem.Crypto.route) { CryptoScreen(navController) }
            composable(BottomNavItem.Cards.route) { CardsScreen(navController) }
            composable(BottomNavItem.Insights.route) { InsightsScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("settings") { SettingsScreen(navController) }
            composable("send") { SendScreen(navController) }
            composable("receive") { ReceiveScreen(navController) }
            composable("swap") { SwapScreen(navController) }
            composable("scan") { ScanScreen(navController) }
            composable("transactions") { TransactionsScreen(navController) }
            composable("card_details") { CardDetailsScreen(navController) }
            composable("card_limits") { CardLimitsScreen(navController) }
            composable("binance_connect") { BinanceConnectScreen(navController) }
            composable("assistant") { AssistantScreen(navController) }
            composable("zakat") { ZakatScreen(navController) }
            composable("sadaqa") { SadaqaScreen(navController) }
            composable("family") { FamilyScreen(navController) }
            composable("kid_account/{kidName}") { backStackEntry ->
                val kidName = backStackEntry.arguments?.getString("kidName") ?: "Kid"
                KidAccountScreen(navController, kidName)
            }
        }

        // Floating Assistant Bubble (right-bottom)
        if (currentRoute in items.map { it.route }) {
            AssistantBubble(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 100.dp),
                onClick = {
                    navController.navigate("assistant")
                }
            )
        }

        // Floating bottom nav
        if (currentRoute in items.map { it.route }) {
            FloatingBottomNav(
                items = items,
                currentRoute = currentRoute ?: "",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                onClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun FloatingBottomNav(
    items: List<BottomNavItem>,
    currentRoute: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val haptic = rememberHaptic()
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFF1A2032).copy(alpha = 0.95f))
            .padding(horizontal = 6.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        if (isSelected) Brush.linearGradient(listOf(Primary, AccentTeal))
                        else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                    )
                    .springClickable()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!isSelected) {
                            haptic.trigger(HapticType.SELECTION)
                            onClick(item.route)
                        }
                    }
                    .padding(
                        horizontal = if (isSelected) 14.dp else 10.dp,
                        vertical = 10.dp
                    )
            ) {
                NavIcon(item.icon, isSelected)
                if (isSelected) {
                    Spacer(Modifier.width(6.dp))
                    Text(
                        item.label,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun NavIcon(name: String, selected: Boolean) {
    val color = if (selected) Color.White else TextSecondary
    val symbol = when (name) {
        "home" -> "◉"
        "diamond" -> "◆"
        "card" -> "▭"
        "chart" -> "◢"
        else -> "•"
    }
    Text(symbol, fontSize = 18.sp, color = color, fontWeight = FontWeight.Bold)
}

@Composable
private fun AssistantBubble(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val haptic = rememberHaptic()

    // Pulse animation
    val infinite = rememberInfiniteTransition(label = "assistantPulse")
    val pulse by infinite.animateFloat(
        initialValue = 1f, targetValue = 1.12f,
        animationSpec = infiniteRepeatable(tween(1800), RepeatMode.Reverse),
        label = "p"
    )
    val glow by infinite.animateFloat(
        initialValue = 0.3f, targetValue = 0.6f,
        animationSpec = infiniteRepeatable(tween(1800), RepeatMode.Reverse),
        label = "g"
    )

    Box(
        modifier = modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow
        Box(
            modifier = Modifier
                .scale(pulse)
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            AccentPurple.copy(alpha = glow),
                            Color.Transparent
                        )
                    )
                )
        )

        // Main bubble
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue)))
                .border(2.dp, Color.White.copy(alpha = 0.25f), CircleShape)
                .springClickable()
                .clickable {
                    haptic.trigger(HapticType.MEDIUM)
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "AI",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )
        }

        // Online indicator dot
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(12.dp)
                .clip(CircleShape)
                .background(GreenPositive)
                .border(2.dp, BackgroundDark, CircleShape)
        )
    }
}