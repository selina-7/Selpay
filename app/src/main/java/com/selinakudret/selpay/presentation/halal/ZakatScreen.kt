package com.selinakudret.selpay.presentation.halal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
fun ZakatScreen(navController: NavController) {
    var cash by remember { mutableStateOf("4250") }
    var gold by remember { mutableStateOf("0") }
    var investments by remember { mutableStateOf("980") }
    var business by remember { mutableStateOf("0") }
    var debts by remember { mutableStateOf("0") }

    val total = (cash.toFloatOrNull() ?: 0f) + (gold.toFloatOrNull() ?: 0f) +
            (investments.toFloatOrNull() ?: 0f) + (business.toFloatOrNull() ?: 0f)
    val net = total - (debts.toFloatOrNull() ?: 0f)
    val nisab = 1830f
    val isAboveNisab = net >= nisab
    val zakat = if (isAboveNisab) net * 0.025f else 0f
    val halalGreen = Color(0xFF14B886)

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
            Text("Zakat Calculator", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(halalGreen, Color(0xFF0E7C60))))
                    .padding(24.dp)
            ) {
                Column {
                    Text("Annual Zakat Amount", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "KWD ${"%,.2f".format(zakat)}",
                        color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (isAboveNisab) "Above Nisab — Zakat is obligatory"
                        else "Below Nisab — Zakat not required",
                        color = Color.White, fontSize = 12.sp
                    )
                    Spacer(Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Nisab: KWD ${nisab.toInt()} (85g gold)", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Your Assets", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            ZakatField("Cash & Savings", cash, halalGreen) { cash = it }
            Spacer(Modifier.height(10.dp))
            ZakatField("Gold & Silver Value", gold, halalGreen) { gold = it }
            Spacer(Modifier.height(10.dp))
            ZakatField("Investments & Crypto", investments, halalGreen) { investments = it }
            Spacer(Modifier.height(10.dp))
            ZakatField("Business Inventory", business, halalGreen) { business = it }

            Spacer(Modifier.height(20.dp))
            Text("Deductions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            ZakatField("Outstanding Debts", debts, AccentRose) { debts = it }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(18.dp))
                    .background(CardDark).border(1.dp, GlassBorder, RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Column {
                    SummaryRow("Total Assets", "KWD ${"%,.2f".format(total)}")
                    Spacer(Modifier.height(8.dp))
                    SummaryRow("Net Wealth", "KWD ${"%,.2f".format(net)}")
                    Spacer(Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(GlassBorder))
                    Spacer(Modifier.height(8.dp))
                    SummaryRow("Zakat (2.5%)", "KWD ${"%,.2f".format(zakat)}", halalGreen)
                }
            }

            Spacer(Modifier.height(20.dp))

            if (isAboveNisab) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(halalGreen)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Pay Zakat Now", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(CardDark)
                        .border(1.dp, halalGreen, RoundedCornerShape(14.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Set up monthly payment plan", color = halalGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ZakatField(label: String, value: String, accent: Color, onChange: (String) -> Unit) {
    Column {
        Text(label, color = TextSecondary, fontSize = 11.sp)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onChange(it.filter { c -> c.isDigit() || c == '.' }) },
            prefix = { Text("KWD ", color = TextSecondary, fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accent,
                unfocusedBorderColor = TextMuted,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = accent
            )
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String, color: Color = TextPrimary) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}