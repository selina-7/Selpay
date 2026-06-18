package com.selinakudret.selpay.presentation.family

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.ui.theme.*

@Composable
fun KidAccountScreen(navController: NavController, kidName: String) {
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
                    .size(40.dp).clip(CircleShape).background(CardDark)
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) { Text("‹", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(16.dp))
            Text("$kidName's Account", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Balance card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(AccentBlue, AccentPurple)))
                    .padding(24.dp)
            ) {
                Column {
                    Text("Current Balance", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("KWD 45.00", color = Color.White, fontWeight = FontWeight.Black, fontSize = 32.sp)
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        WhiteChip("Send money")
                        WhiteChip("Top up")
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Allowance card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .border(1.dp, AccentTeal.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Weekly Allowance", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Active", color = AccentTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("KWD 20.00", color = AccentTeal, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Text("Next transfer: Sunday, 09:00", color = TextSecondary, fontSize = 11.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Limits
            Text("Spending Limits", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            LimitProgressCard("Daily limit", 18f, 30f, AccentBlue)
            Spacer(Modifier.height(10.dp))
            LimitProgressCard("Weekly limit", 87f, 100f, AccentPurple)
            Spacer(Modifier.height(10.dp))
            LimitProgressCard("Online purchases", 12f, 50f, AccentRose)

            Spacer(Modifier.height(24.dp))

            // Restrictions
            Text("Restrictions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            RestrictionRow("ATM Withdrawals", "Allowed", true)
            Spacer(Modifier.height(6.dp))
            RestrictionRow("Online Shopping", "Approval required", true)
            Spacer(Modifier.height(6.dp))
            RestrictionRow("Gaming Stores", "Blocked", false)
            Spacer(Modifier.height(6.dp))
            RestrictionRow("Restaurants", "Allowed", true)

            Spacer(Modifier.height(24.dp))

            // Chores
            Text("Chore Rewards", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            ChoreRow("Clean room", "KWD 2.00", true)
            Spacer(Modifier.height(8.dp))
            ChoreRow("Do homework", "KWD 1.50", true)
            Spacer(Modifier.height(8.dp))
            ChoreRow("Help with dishes", "KWD 1.00", false)
            Spacer(Modifier.height(8.dp))
            ChoreRow("Read 30 minutes", "KWD 2.00", false)

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun WhiteChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.2f))
            .clickable { }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) { Text(label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
}

@Composable
private fun LimitProgressCard(label: String, current: Float, max: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text("KWD ${current.toInt()} / ${max.toInt()}", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { current / max },
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.15f)
            )
        }
    }
}

@Composable
private fun RestrictionRow(label: String, status: String, allowed: Boolean) {
    val color = if (allowed) GreenPositive else RedNegative
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextPrimary, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) { Text(status, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun ChoreRow(task: String, reward: String, completed: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (completed) GreenPositive else Color.Transparent)
                .border(2.dp, if (completed) GreenPositive else TextMuted, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (completed) Text("✓", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(12.dp))
        Text(task, color = if (completed) TextSecondary else TextPrimary, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text(reward, color = if (completed) GreenPositive else AccentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}