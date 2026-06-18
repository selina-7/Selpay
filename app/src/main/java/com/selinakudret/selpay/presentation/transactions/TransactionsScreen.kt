package com.selinakudret.selpay.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.EmptyState
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.SkeletonBox
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.ui.theme.*
import kotlinx.coroutines.delay

data class Transaction(
    val title: String,
    val category: String,
    val date: String,
    val daysAgo: Int,
    val amount: Float,
    val positive: Boolean,
    val merchant: String
)

@Composable
fun TransactionsScreen(navController: NavController) {
    val currency by LocalCurrency.current
    val haptic = rememberHaptic()
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("week") }
    var loading by remember { mutableStateOf(true) }

    // Simulate loading
    LaunchedEffect(filter, query) {
        loading = true
        delay(400)
        loading = false
    }

    val allTx = remember {
        listOf(
            Transaction("Netflix", "Subscription", "Today, 19:42", 0, 3.50f, false, "Netflix Inc"),
            Transaction("Salary Deposit", "Income", "Today, 09:00", 0, 850.00f, true, "Employer"),
            Transaction("Carrefour", "Groceries", "Yesterday, 18:21", 1, 22.10f, false, "Carrefour Kuwait"),
            Transaction("Transfer to Ali", "Sent", "Yesterday, 14:05", 1, 50.00f, false, "Ali Hassan"),
            Transaction("Uber", "Transport", "2 days ago", 2, 4.20f, false, "Uber"),
            Transaction("Refund - Amazon", "Refund", "May 26", 5, 18.40f, true, "Amazon"),
            Transaction("Spotify", "Subscription", "May 24", 7, 4.00f, false, "Spotify"),
            Transaction("Salary Bonus", "Income", "May 20", 11, 200.00f, true, "Employer"),
            Transaction("Apple Store", "Shopping", "May 18", 13, 145.00f, false, "Apple"),
            Transaction("KFH Loan Payment", "Bills", "May 15", 16, 180.00f, false, "KFH"),
            Transaction("Talabat Food", "Food", "May 12", 19, 8.50f, false, "Talabat"),
            Transaction("iCloud Storage", "Subscription", "May 10", 21, 0.99f, false, "Apple"),
            Transaction("Salary Deposit", "Income", "May 1", 30, 850.00f, true, "Employer"),
            Transaction("Furniture - IKEA", "Shopping", "Apr 28", 33, 320.00f, false, "IKEA"),
            Transaction("Electricity Bill", "Bills", "Apr 25", 36, 32.50f, false, "MEW"),
            Transaction("Doctor Visit", "Health", "Apr 20", 41, 25.00f, false, "Dar Al Shifa"),
            Transaction("Refund - Booking", "Refund", "Apr 15", 46, 120.00f, true, "Booking.com"),
            Transaction("Petrol Station", "Transport", "Apr 10", 51, 12.00f, false, "Q8 Petrol"),
            Transaction("Pharmacy", "Health", "Apr 5", 56, 7.80f, false, "Boots Kuwait"),
            Transaction("Internet Bill", "Bills", "Apr 1", 60, 22.00f, false, "Ooredoo")
        )
    }

    val days = when (filter) {
        "week" -> 7
        "month" -> 30
        "3months" -> 90
        else -> 365
    }

    val filtered = allTx.filter {
        it.daysAgo <= days &&
                (query.isBlank() ||
                        it.title.contains(query, ignoreCase = true) ||
                        it.merchant.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true))
    }

    val totalIn = filtered.filter { it.positive }.sumOf { it.amount.toDouble() }.toFloat()
    val totalOut = filtered.filter { !it.positive }.sumOf { it.amount.toDouble() }.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
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
            Text("Transactions", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Search (clean)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardDark)
                    .border(0.5.dp, GlassBorder, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⌕", color = TextSecondary, fontSize = 16.sp)
                Spacer(Modifier.width(10.dp))
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    textStyle = TextStyle(color = TextPrimary, fontSize = 14.sp),
                    cursorBrush = SolidColor(Primary),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    decorationBox = { inner ->
                        if (query.isEmpty()) {
                            Text("Search transactions, merchants...", color = TextMuted, fontSize = 13.sp)
                        }
                        inner()
                    }
                )
                if (query.isNotEmpty()) {
                    Text(
                        "✕",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            haptic.trigger(HapticType.LIGHT)
                            query = ""
                        }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // Filters
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip("1W", filter == "week") {
                    haptic.trigger(HapticType.SELECTION)
                    filter = "week"
                }
                FilterChip("1M", filter == "month") {
                    haptic.trigger(HapticType.SELECTION)
                    filter = "month"
                }
                FilterChip("3M", filter == "3months") {
                    haptic.trigger(HapticType.SELECTION)
                    filter = "3months"
                }
                FilterChip("All", filter == "all") {
                    haptic.trigger(HapticType.SELECTION)
                    filter = "all"
                }
            }

            Spacer(Modifier.height(16.dp))

            // Summary
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryCard("Income", "+${currency.code} ${"%,.0f".format(totalIn)}", GreenPositive, Modifier.weight(1f))
                SummaryCard("Spent", "-${currency.code} ${"%,.0f".format(totalOut)}", AccentRose, Modifier.weight(1f))
            }

            Spacer(Modifier.height(8.dp))
            Text("${filtered.size} transactions", color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp))
            Spacer(Modifier.height(12.dp))
        }

        // List
        if (loading) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(6) {
                    SkeletonRow()
                }
            }
        } else if (filtered.isEmpty()) {
            EmptyState(
                icon = "⌕",
                title = "No transactions found",
                description = if (query.isNotEmpty()) "Try a different search term" else "Try a different time range"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filtered) { tx -> TxRow(tx, currency.code) }
                item { Spacer(Modifier.height(40.dp)) }
            }
        }
    }
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Primary else CardDark)
            .border(0.5.dp, if (selected) Primary else GlassBorder, RoundedCornerShape(12.dp))
            .springClickable()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            label,
            color = if (selected) Color.White else TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SummaryCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(0.5.dp, color.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(value, color = color, fontWeight = FontWeight.Black, fontSize = 16.sp)
        }
    }
}

@Composable
private fun TxRow(tx: Transaction, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .clickable { }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp).clip(CircleShape)
                    .background(if (tx.positive) GreenPositive.copy(alpha = 0.15f) else CardElevated),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (tx.positive) "↓" else "↑",
                    color = if (tx.positive) GreenPositive else TextSecondary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(tx.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text("${tx.category}  •  ${tx.date}", color = TextSecondary, fontSize = 11.sp)
            }
            Text(
                "${if (tx.positive) "+" else "-"}$currency ${"%,.2f".format(tx.amount)}",
                color = if (tx.positive) GreenPositive else TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun SkeletonRow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SkeletonBox(modifier = Modifier.size(40.dp), cornerRadius = 20.dp)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                SkeletonBox(modifier = Modifier.fillMaxWidth(0.5f).height(14.dp))
                Spacer(Modifier.height(6.dp))
                SkeletonBox(modifier = Modifier.fillMaxWidth(0.3f).height(11.dp))
            }
            SkeletonBox(modifier = Modifier.width(70.dp).height(14.dp))
        }
    }
}