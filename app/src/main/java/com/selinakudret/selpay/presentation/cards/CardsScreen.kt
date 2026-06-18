package com.selinakudret.selpay.presentation.cards

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.DynamicNotification
import com.selinakudret.selpay.presentation.effects.DynamicNotificationOverlay
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.SelPayBottomSheet
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.ui.theme.*
import kotlin.math.absoluteValue

data class PremiumCard(
    val name: String,
    val last4: String,
    val gradient: List<Color>,
    val type: String,
    val balance: Float,
    val limit: Float
)

@Composable
fun CardsScreen(navController: NavController) {
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()

    val cards = remember {
        mutableStateListOf(
            PremiumCard("Premium Card", "4821", listOf(GradientStart, GradientMid, GradientEnd), "Visa Platinum", 4250f, 5000f),
            PremiumCard("Online Shopping", "7392", listOf(AccentRose, AccentPurple), "Virtual", 850f, 1000f),
            PremiumCard("Subscriptions", "1056", listOf(AccentTeal, NeonCyan), "Virtual", 120f, 200f),
            PremiumCard("Travel Card", "8843", listOf(BitcoinOrange, AccentRose), "Travel", 2300f, 3000f)
        )
    }
    val frozenStates = remember { mutableStateMapOf<Int, Boolean>() }

    val pagerState = rememberPagerState(pageCount = { cards.size })
    val currentCard = cards[pagerState.currentPage]
    val isFrozen = frozenStates[pagerState.currentPage] == true

    var showActionsSheet by remember { mutableStateOf(false) }
    var notification by remember { mutableStateOf<DynamicNotification?>(null) }

    // Shimmer animation
    val infinite = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by infinite.animateFloat(
        initialValue = -200f, targetValue = 600f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "sh"
    )

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
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
                Column {
                    Text("My Cards", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 26.sp)
                    Text("${cards.size} cards", color = TextSecondary, fontSize = 12.sp)
                }
                Box(
                    modifier = Modifier
                        .size(44.dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Primary, AccentTeal)))
                        .springClickable()
                        .clickable {
                            haptic.trigger(HapticType.MEDIUM)
                            notification = DynamicNotification(
                                icon = "+",
                                title = "New Card",
                                subtitle = "Order will arrive in 3-5 days",
                                accentColor = Primary
                            )
                        },
                    contentAlignment = Alignment.Center
                ) { Text("+", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black) }
            }

            Spacer(Modifier.height(24.dp))

            // Card carousel with 3D effect
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 30.dp),
                pageSpacing = 14.dp
            ) { page ->
                val offset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                val scale = lerp(0.92f, 1f, 1f - offset.coerceIn(0f, 1f))
                val rot = lerp(-8f, 0f, 1f - offset.coerceIn(0f, 1f))

                Box(
                    modifier = Modifier
                        .scale(scale)
                        .rotate(if (page > pagerState.currentPage) -rot else rot)
                ) {
                    PremiumCardView(
                        card = cards[page],
                        frozen = frozenStates[page] == true,
                        shimmerOffset = shimmerX,
                        currency = currency.code
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Page dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(cards.size) { i ->
                    val isSel = i == pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(width = if (isSel) 24.dp else 8.dp, height = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSel) Primary else TextMuted)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Spending limit ring
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(CardDark)
                    .border(0.5.dp, GlassBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Available Spending", color = TextSecondary, fontSize = 11.sp)
                            Text(
                                "${currency.code} ${"%,.2f".format(currentCard.balance)}",
                                color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 22.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Daily Limit", color = TextSecondary, fontSize = 11.sp)
                            Text(
                                "${currency.code} ${"%,.0f".format(currentCard.limit)}",
                                color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    LinearProgressIndicator(
                        progress = { currentCard.balance / currentCard.limit },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(6.dp)),
                        color = Primary,
                        trackColor = Primary.copy(alpha = 0.15f)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Card actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CardActionButton(
                    if (isFrozen) "◉" else "◆",
                    if (isFrozen) "Unfreeze" else "Freeze",
                    if (isFrozen) AccentBlue else AccentRose,
                    Modifier.weight(1f)
                ) {
                    haptic.trigger(HapticType.MEDIUM)
                    val newState = !isFrozen
                    frozenStates[pagerState.currentPage] = newState
                    notification = DynamicNotification(
                        icon = if (newState) "◆" else "◉",
                        title = if (newState) "Card Frozen" else "Card Active",
                        subtitle = if (newState) "Payments blocked" else "Ready to use",
                        accentColor = if (newState) AccentRose else GreenPositive
                    )
                }
                CardActionButton("◫", "Details", AccentPurple, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.LIGHT)
                    navController.navigate("card_details")
                }
                CardActionButton("◈", "Limits", AccentTeal, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.LIGHT)
                    navController.navigate("card_limits")
                }
                CardActionButton("⋯", "More", AccentGold, Modifier.weight(1f)) {
                    haptic.trigger(HapticType.LIGHT)
                    showActionsSheet = true
                }
            }

            Spacer(Modifier.height(28.dp))

            // Recent on this card
            Text("Recent on this card", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .padding(vertical = 4.dp)
            ) {
                Column {
                    MiniTxRow("Apple Store", "Shopping", "-145.00", currency.code)
                    MiniTxDivider()
                    MiniTxRow("Carrefour", "Groceries", "-22.10", currency.code)
                    MiniTxDivider()
                    MiniTxRow("Uber", "Transport", "-4.20", currency.code)
                }
            }
        }

        // Dynamic notification (Dynamic Island style)
        DynamicNotificationOverlay(notification = notification) {
            notification = null
        }

        // More actions bottom sheet
        SelPayBottomSheet(
            visible = showActionsSheet,
            onDismiss = { showActionsSheet = false }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text("Card Actions", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 20.sp)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${currentCard.name} • ${currentCard.last4}",
                    color = TextSecondary, fontSize = 12.sp
                )

                Spacer(Modifier.height(20.dp))

                SheetAction("◇", "Add to Apple Pay", "Use card on iPhone & Watch") {
                    haptic.trigger(HapticType.MEDIUM)
                    showActionsSheet = false
                    notification = DynamicNotification(
                        icon = "✓",
                        title = "Added to Apple Pay",
                        subtitle = "Ready to use on your devices",
                        accentColor = GreenPositive
                    )
                }
                SheetAction("◆", "Replace Card", "Lost or damaged") {
                    haptic.trigger(HapticType.LIGHT)
                }
                SheetAction("◢", "Change PIN", "Update card PIN code") {
                    haptic.trigger(HapticType.LIGHT)
                }
                SheetAction("⌕", "Report Issue", "Disputes, fraud, problems") {
                    haptic.trigger(HapticType.LIGHT)
                }
                SheetAction("✕", "Close Card", "Permanently close this card", AccentRose) {
                    haptic.trigger(HapticType.WARNING)
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(CardDark)
                        .clickable { showActionsSheet = false }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cancel", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun SheetAction(
    icon: String,
    title: String,
    subtitle: String,
    iconColor: Color = TextPrimary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp).clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, color = iconColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = iconColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, color = TextSecondary, fontSize = 11.sp)
        }
        Text("›", color = TextSecondary, fontSize = 20.sp)
    }
}

@Composable
private fun PremiumCardView(
    card: PremiumCard,
    frozen: Boolean,
    shimmerOffset: Float,
    currency: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                if (frozen) Brush.linearGradient(listOf(Color.Gray, Color.DarkGray))
                else Brush.linearGradient(card.gradient)
            )
    ) {
        if (!frozen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            start = androidx.compose.ui.geometry.Offset(shimmerOffset, 0f),
                            end = androidx.compose.ui.geometry.Offset(shimmerOffset + 200f, 200f)
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(140.dp)
                .background(Color.White.copy(alpha = 0.12f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .size(120.dp)
                .background(Color.White.copy(alpha = 0.08f), CircleShape)
        )

        Column(modifier = Modifier.fillMaxSize().padding(22.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("SelPay", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                    Text(card.type, color = Color.White.copy(alpha = 0.75f), fontSize = 10.sp)
                }
                if (frozen) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.3f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) { Text("FROZEN", color = Color.White, fontWeight = FontWeight.Black, fontSize = 11.sp) }
                } else {
                    Box(
                        modifier = Modifier
                            .size(width = 36.dp, height = 26.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) { Text("◫", color = Color.White, fontSize = 14.sp) }
                }
            }

            Spacer(Modifier.weight(1f))

            Text("Balance", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
            Text(
                "$currency ${"%,.2f".format(card.balance)}",
                color = Color.White, fontWeight = FontWeight.Black, fontSize = 22.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "•••• •••• •••• ${card.last4}",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text("CARDHOLDER", color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp)
                    Text("SELINA KUDRET", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("EXPIRES", color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp)
                    Text("12/28", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text("VISA", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun CardActionButton(
    symbol: String,
    label: String,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(0.5.dp, accent.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
            .springClickable()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(36.dp).clip(CircleShape)
                .background(accent.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) { Text(symbol, color = accent, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
        Spacer(Modifier.height(6.dp))
        Text(label, color = accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MiniTxRow(title: String, category: String, amount: String, currency: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 13.sp)
            Text(category, color = TextSecondary, fontSize = 11.sp)
        }
        Text(
            "$amount $currency",
            color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp
        )
    }
}

@Composable
private fun MiniTxDivider() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .height(0.5.dp).background(GlassBorder)
    )
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float = start + (stop - start) * fraction