package com.selinakudret.selpay.presentation.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SwapScreen(navController: NavController) {
    var fromAmount by remember { mutableStateOf("100.00") }
    var fromCurrency by remember { mutableStateOf("KWD") }
    var toCurrency by remember { mutableStateOf("BTC") }
    val rate = 0.0000148f // dummy
    val toAmount = (fromAmount.toFloatOrNull() ?: 0f) * 0.0000148f * 10000

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(title = "Swap", navController)

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // From
            CurrencyCard(
                label = "You pay",
                currency = fromCurrency,
                amount = fromAmount,
                onAmountChange = { fromAmount = it },
                balance = "Balance: 4,250.00 $fromCurrency",
                color = AccentBlue
            )

            // Swap arrow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue)))
                        .clickable {
                            val tmp = fromCurrency
                            fromCurrency = toCurrency
                            toCurrency = tmp
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("⇅", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }

            // To
            CurrencyCard(
                label = "You receive",
                currency = toCurrency,
                amount = "%.6f".format(toAmount),
                onAmountChange = null,
                balance = "Balance: 0.0241 $toCurrency",
                color = BitcoinOrange,
                readOnly = true
            )

            Spacer(Modifier.height(20.dp))

            // Rate info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardDark)
                    .padding(14.dp)
            ) {
                Column {
                    RateRow("Exchange Rate", "1 KWD ≈ 0.0000148 BTC")
                    Spacer(Modifier.height(6.dp))
                    RateRow("Fee", "0.5%")
                    Spacer(Modifier.height(6.dp))
                    RateRow("You receive", "≈ ${"%.6f".format(toAmount)} $toCurrency")
                }
            }

            Spacer(Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Text("Confirm Swap", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
private fun CurrencyCard(
    label: String,
    currency: String,
    amount: String,
    onAmountChange: ((String) -> Unit)?,
    balance: String,
    color: Color,
    readOnly: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            Text(label, color = TextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(color.copy(alpha = 0.2f))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(currency, color = color, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(Modifier.width(6.dp))
                        Text("▾", color = color, fontSize = 12.sp)
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    amount,
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(balance, color = TextSecondary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun RateRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 12.sp)
        Text(value, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}