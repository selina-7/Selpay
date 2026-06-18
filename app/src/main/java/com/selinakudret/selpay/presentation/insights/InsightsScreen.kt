package com.selinakudret.selpay.presentation.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.presentation.theme.LocalHalalMode
import com.selinakudret.selpay.ui.theme.*

@Composable
fun InsightsScreen(navController: NavController) {
    val halalMode by LocalHalalMode.current
    val currency by LocalCurrency.current
    val halalGreen = Color(0xFF14B886)
    var period by remember { mutableStateOf("Month") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Insights", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Black)
                Text("Your financial overview", color = TextSecondary, fontSize = 12.sp)
            }
            Box(
                modifier = Modifier
                    .size(42.dp).clip(CircleShape).background(CardDark)
                    .border(1.dp, GlassBorder, CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) { Text("⇣", color = TextPrimary, fontSize = 16.sp) }
        }

        Spacer(Modifier.height(16.dp))

        // Period selector
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PeriodChip("Week", period == "Week") { period = "Week" }
            PeriodChip("Month", period == "Month") { period = "Month" }
            PeriodChip("3 Months", period == "3 Months") { period = "3 Months" }
            PeriodChip("Year", period == "Year") { period = "Year" }
        }

        Spacer(Modifier.height(22.dp))

        // Hero stat
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(listOf(GradientStart, AccentBlue)))
                .padding(22.dp)
        ) {
            Column {
                Text("Net Worth", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${currency.code} 12,480.50",
                    color = Color.White, fontWeight = FontWeight.Black, fontSize = 36.sp
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.25f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) { Text("▴ 8.2% ($940)", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.width(8.dp))
                    Text("vs last month", color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
                }
                Spacer(Modifier.height(18.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    WhiteStatItem("Assets", "${currency.code} 13,200")
                    WhiteStatItem("Liabilities", "${currency.code} 720")
                    WhiteStatItem("Cash", "${currency.code} 4,250")
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        // KPI grid (2x2)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KpiCard("Savings Rate", "32%", "▴ 5%", true, AccentTeal, Modifier.weight(1f))
            KpiCard("Income", "${currency.code} 1,050", "Same", false, GreenPositive, Modifier.weight(1f))
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KpiCard("Expenses", "${currency.code} 482", "▾ 23%", true, AccentRose, Modifier.weight(1f))
            KpiCard("Investments", "${currency.code} 8,420", "▴ 3%", true, AccentPurple, Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // Cash flow chart
        Text("Cash Flow Trend", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))
        CashFlowCard(currency.code)

        Spacer(Modifier.height(24.dp))

        // Halal wealth
        if (halalMode) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Brush.linearGradient(listOf(halalGreen.copy(alpha = 0.25f), CardDark)))
                    .border(1.dp, halalGreen.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                    .clickable { navController.navigate("zakat") }
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(50.dp).clip(CircleShape).background(halalGreen),
                        contentAlignment = Alignment.Center
                    ) { Text("☪", color = Color.White, fontSize = 26.sp) }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Zakat-Eligible Wealth", color = halalGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(
                            "${currency.code} 5,230",
                            color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black
                        )
                        Text("Above Nisab • Zakat: ${currency.code} 130.75", color = TextSecondary, fontSize = 11.sp)
                    }
                    Text("›", color = halalGreen, fontSize = 22.sp)
                }
            }
            Spacer(Modifier.height(22.dp))
        }

        // Smart suggestions
        Text("Smart Suggestions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))

        SuggestionCard("AI", "Cancel unused subscription", "You haven't used AppMaster Pro in 4 months. Save ${currency.code} 60/year by canceling.", AccentPurple)
        Spacer(Modifier.height(10.dp))
        SuggestionCard("◆", "Move to high-yield savings", "Your cash earns 0%. Move ${currency.code} 2,000 to savings for ${currency.code} 70/year extra.", AccentTeal)
        Spacer(Modifier.height(10.dp))
        SuggestionCard("▲", "You're on track for goals", "At current rate, Istanbul Trip funded in 3 months. Boost by ${currency.code} 50/week to finish 1 month early.", GreenPositive)

        Spacer(Modifier.height(24.dp))

        // Top merchants
        Text("Top Merchants", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))

        MerchantBar("Carrefour", "Groceries", 62.40f, 0.85f, AccentTeal, currency.code)
        Spacer(Modifier.height(8.dp))
        MerchantBar("Amazon", "Shopping", 145.00f, 0.70f, AccentRose, currency.code)
        Spacer(Modifier.height(8.dp))
        MerchantBar("Talabat", "Food", 48.50f, 0.55f, AccentGold, currency.code)
        Spacer(Modifier.height(8.dp))
        MerchantBar("KFH Loan", "Bills", 180.00f, 0.95f, AccentPurple, currency.code)

        Spacer(Modifier.height(24.dp))

        // Subscriptions
        Text("Active Subscriptions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CardDark)
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
        ) {
            Column {
                SubRow("Netflix", "Monthly", 3.50f, false, currency.code)
                SubDivider()
                SubRow("Spotify", "Monthly", 4.00f, false, currency.code)
                SubDivider()
                SubRow("iCloud", "Monthly", 0.99f, false, currency.code)
                SubDivider()
                SubRow("AppMaster Pro", "Monthly", 5.00f, true, currency.code)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Upcoming bills
        Text("Upcoming Bills (30 days)", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CardDark)
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
        ) {
            Column {
                BillRow("Electricity", "Jun 12", "Auto-pay", 32.50f, currency.code)
                SubDivider()
                BillRow("Internet", "Jun 15", "Auto-pay", 22.00f, currency.code)
                SubDivider()
                BillRow("KFH Loan", "Jun 20", "Manual", 180.00f, currency.code)
                SubDivider()
                BillRow("Insurance", "Jun 28", "Auto-pay", 45.00f, currency.code)
            }
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun PeriodChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Primary else CardDark)
            .border(1.dp, if (selected) Primary else GlassBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            label,
            color = if (selected) Color.White else TextPrimary,
            fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun WhiteStatItem(label: String, value: String) {
    Column {
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@Composable
private fun KpiCard(label: String, value: String, change: String, isPositive: Boolean, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(8.dp).clip(CircleShape).background(color)
                )
                Spacer(Modifier.width(6.dp))
                Text(label, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(value, color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 20.sp)
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isPositive) GreenPositive.copy(alpha = 0.15f) else TextMuted.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    change,
                    color = if (isPositive) GreenPositive else TextSecondary,
                    fontSize = 10.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun CashFlowCard(currency: String) {
    val incomeData = listOf(800f, 850f, 850f, 1050f, 850f, 1050f)
    val expenseData = listOf(620f, 540f, 690f, 482f, 580f, 482f)
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
    val maxValue = (incomeData + expenseData).max()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(CardDark)
            .border(1.dp, GlassBorder, RoundedCornerShape(22.dp))
            .padding(18.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(GreenPositive))
                    Spacer(Modifier.width(6.dp))
                    Text("Income", color = TextSecondary, fontSize = 11.sp)
                }
                Spacer(Modifier.width(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(AccentRose))
                    Spacer(Modifier.width(6.dp))
                    Text("Expenses", color = TextSecondary, fontSize = 11.sp)
                }
                Spacer(Modifier.weight(1f))
                Text("Net: +$currency 568", color = GreenPositive, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().height(130.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                months.forEachIndexed { i, month ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height((incomeData[i] / maxValue * 110).dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Brush.verticalGradient(listOf(GreenPositive, GreenPositive.copy(alpha = 0.5f))))
                            )
                            Spacer(Modifier.width(3.dp))
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height((expenseData[i] / maxValue * 110).dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Brush.verticalGradient(listOf(AccentRose, AccentRose.copy(alpha = 0.5f))))
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            month,
                            color = if (i == months.lastIndex) AccentBlue else TextSecondary,
                            fontSize = 10.sp,
                            fontWeight = if (i == months.lastIndex) FontWeight.Black else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionCard(icon: String, title: String, body: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            .clickable { }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) { Text(icon, color = color, fontWeight = FontWeight.Black, fontSize = 13.sp) }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = color, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(Modifier.height(4.dp))
                Text(body, color = TextSecondary, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun MerchantBar(name: String, category: String, amount: Float, progress: Float, color: Color, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .padding(14.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(category, color = TextSecondary, fontSize = 11.sp)
                }
                Text("$currency ${"%.2f".format(amount)}", color = color, fontWeight = FontWeight.Black, fontSize = 14.sp)
            }
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(4.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.15f)
            )
        }
    }
}

@Composable
private fun SubRow(name: String, period: String, amount: Float, unused: Boolean, currency: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                if (unused) {
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                            .background(Warning.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) { Text("UNUSED 4mo", color = Warning, fontSize = 9.sp, fontWeight = FontWeight.Black) }
                }
            }
            Text(period, color = TextSecondary, fontSize = 11.sp)
        }
        Text("$currency ${"%.2f".format(amount)}", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@Composable
private fun SubDivider() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp)
            .height(0.5.dp).background(GlassBorder)
    )
}

@Composable
private fun BillRow(name: String, date: String, status: String, amount: Float, currency: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(date, color = TextSecondary, fontSize = 11.sp)
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (status == "Auto-pay") GreenPositive.copy(alpha = 0.15f) else Warning.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                    Text(
                        status,
                        color = if (status == "Auto-pay") GreenPositive else Warning,
                        fontSize = 9.sp, fontWeight = FontWeight.Black
                    )
                }
            }
        }
        Text("$currency ${"%.2f".format(amount)}", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}