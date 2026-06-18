package com.selinakudret.selpay.presentation.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.navigation.Screen
import com.selinakudret.selpay.ui.theme.*
import kotlinx.coroutines.launch

private data class OnboardPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String,
    val gradient: List<Color>
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val haptic = rememberHaptic()
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardPage(
            title = "Welcome to SelPay",
            subtitle = "Where banking meets belief.",
            description = "A premium fintech experience built for the Middle East. Designed with care, engineered for trust.",
            icon = "S",
            gradient = listOf(GradientStart, GradientMid, GradientEnd)
        ),
        OnboardPage(
            title = "Halal Banking",
            subtitle = "Sharia-Compliant Finance",
            description = "Zakat calculator, Sadaqa tracker, and Nisab calculations — all built-in. Banking aligned with your values.",
            icon = "☪",
            gradient = listOf(Color(0xFF14B886), Color(0xFF0E7C60), AccentTeal)
        ),
        OnboardPage(
            title = "Built for the Gulf",
            subtitle = "GCC Banking, Reimagined",
            description = "Multi-currency support across Kuwait, Bahrain, UAE, and the entire Gulf region. Banking that understands your context.",
            icon = "⊕",
            gradient = listOf(AccentBlue, AccentTeal, NeonCyan)
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val isLastPage = pagerState.currentPage == pages.size - 1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        val infinite = rememberInfiniteTransition(label = "obOrb")
        val rotation by infinite.animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(25000, easing = LinearEasing)),
            label = "rot"
        )

        val currentGradient = pages[pagerState.currentPage].gradient

        Box(
            modifier = Modifier
                .offset(x = (-120).dp, y = (-120).dp)
                .size(400.dp)
                .rotate(rotation)
                .background(
                    Brush.radialGradient(
                        listOf(currentGradient[0].copy(alpha = 0.25f), Color.Transparent)
                    ),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 100.dp, y = 100.dp)
                .size(380.dp)
                .rotate(-rotation)
                .background(
                    Brush.radialGradient(
                        listOf(currentGradient.last().copy(alpha = 0.2f), Color.Transparent)
                    ),
                    CircleShape
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isLastPage) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(CardDark)
                            .border(0.5.dp, GlassBorder, RoundedCornerShape(20.dp))
                            .clickable {
                                haptic.trigger(HapticType.LIGHT)
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0)
                                }
                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("Skip", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardPageView(pages[page])
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pages.indices.forEach { i ->
                        val isSel = i == pagerState.currentPage
                        Box(
                            modifier = Modifier
                                .size(width = if (isSel) 28.dp else 8.dp, height = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSel) Primary else TextMuted)
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                        .springClickable()
                        .clickable {
                            haptic.trigger(HapticType.MEDIUM)
                            if (isLastPage) {
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0)
                                }
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (isLastPage) "Get Started" else "Next",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardPageView(page: OnboardPage) {
    val infinite = rememberInfiniteTransition(label = "iconFloat")
    val float by infinite.animateFloat(
        initialValue = -8f, targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "float"
    )
    val pulse by infinite.animateFloat(
        initialValue = 1f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = float.dp)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .scale(pulse)
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(page.gradient[0].copy(alpha = 0.4f), Color.Transparent)
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(Brush.linearGradient(page.gradient))
                    .border(2.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(36.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    page.icon,
                    color = Color.White,
                    fontSize = if (page.icon.length > 1) 44.sp else 64.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(Modifier.height(48.dp))

        Text(
            page.title,
            color = TextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            page.subtitle,
            color = Primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        Text(
            page.description,
            color = TextSecondary,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}