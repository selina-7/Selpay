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
import com.selinakudret.selpay.ui.theme.*

data class FamilyMember(
    val name: String,
    val initials: String,
    val role: String,
    val balance: Float,
    val allowance: Float,
    val accent: Color,
    val age: Int
)

@Composable
fun FamilyScreen(navController: NavController) {
    val members = listOf(
        FamilyMember("Yusuf", "Y", "Son", 45f, 20f, AccentBlue, 12),
        FamilyMember("Layla", "L", "Daughter", 28.50f, 15f, AccentRose, 9),
        FamilyMember("Maria", "M", "Spouse", 850f, 0f, AccentPurple, 32)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar
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
            Text("Family Banking", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Hero card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue)))
                    .padding(20.dp)
            ) {
                Column {
                    Text("Family Wallet", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("3 Members", color = Color.White, fontWeight = FontWeight.Black, fontSize = 26.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("Total balance: KWD 923.50", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Members", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            members.forEach { member ->
                MemberCard(member) {
                    if (member.role == "Son" || member.role == "Daughter") {
                        navController.navigate("kid_account/${member.name}")
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(12.dp))

            // Add member
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(CardDark)
                    .border(1.dp, Primary.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                    .clickable { }
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("+", color = Primary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text("Add family member", color = Primary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(28.dp))

            // Quick actions
            Text("Quick Actions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FamilyActionCard("Set allowance", "Weekly auto-transfer", AccentTeal, Modifier.weight(1f))
                FamilyActionCard("Chore rewards", "Pay for tasks done", AccentGold, Modifier.weight(1f))
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FamilyActionCard("Spending limits", "Per child controls", AccentRose, Modifier.weight(1f))
                FamilyActionCard("Pending approvals", "2 requests waiting", AccentPurple, Modifier.weight(1f))
            }

            Spacer(Modifier.height(28.dp))

            // Recent family activity
            Text("Recent Activity", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            ActivityRow("Yusuf received", "Weekly allowance", "+KWD 20.00", true)
            Divider()
            ActivityRow("Layla spent", "Toy store", "-KWD 8.50", false)
            Divider()
            ActivityRow("Yusuf completed chore", "Cleaned room", "+KWD 2.00", true)
            Divider()
            ActivityRow("Pending: Yusuf", "Wants to buy game (KWD 15)", "Approve", false, isPending = true)

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun MemberCard(member: FamilyMember, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .border(1.dp, member.accent.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(member.accent, member.accent.copy(alpha = 0.6f)))),
                contentAlignment = Alignment.Center
            ) {
                Text(member.initials, color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(member.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(member.accent.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) { Text(member.role, color = member.accent, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                }
                Text("Age ${member.age}", color = TextSecondary, fontSize = 11.sp)
                if (member.allowance > 0) {
                    Spacer(Modifier.height(2.dp))
                    Text("KWD ${member.allowance.toInt()}/week allowance", color = member.accent, fontSize = 11.sp)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("KWD", color = TextSecondary, fontSize = 10.sp)
                Text("${"%.2f".format(member.balance)}", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun FamilyActionCard(title: String, subtitle: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            .clickable { }
            .padding(14.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(6.dp).clip(CircleShape).background(color)
            )
            Spacer(Modifier.height(10.dp))
            Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, color = TextSecondary, fontSize = 11.sp)
        }
    }
}

@Composable
private fun ActivityRow(title: String, subtitle: String, amount: String, positive: Boolean, isPending: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 13.sp)
            Text(subtitle, color = TextSecondary, fontSize = 11.sp)
        }
        if (isPending) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(AccentGold.copy(alpha = 0.2f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) { Text(amount, color = AccentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        } else {
            Text(amount, color = if (positive) GreenPositive else TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun Divider() {
    Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(GlassBorder))
}