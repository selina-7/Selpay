package com.selinakudret.selpay.presentation.crypto

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.ui.theme.*

@Composable
fun BinanceConnectScreen(navController: NavController) {
    var apiKey by remember { mutableStateOf("") }
    var secretKey by remember { mutableStateOf("") }
    var connected by remember { mutableStateOf(false) }

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
            Text("Connect Binance", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Binance logo card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(BinanceYellow.copy(alpha = 0.2f), CardDark)))
                    .border(1.dp, BinanceYellow.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(56.dp).clip(RoundedCornerShape(14.dp))
                            .background(BinanceYellow),
                        contentAlignment = Alignment.Center
                    ) { Text("B", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 28.sp) }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Binance", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 18.sp)
                        Text(
                            if (connected) "Connected" else "Not connected",
                            color = if (connected) GreenPositive else TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Connect your Binance account using API keys. Your keys are encrypted and stored locally.",
                color = TextSecondary, fontSize = 12.sp
            )

            Spacer(Modifier.height(20.dp))

            Text("API Key", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                placeholder = { Text("Enter API key") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BinanceYellow,
                    unfocusedBorderColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = BinanceYellow
                )
            )

            Spacer(Modifier.height(14.dp))

            Text("Secret Key", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = secretKey,
                onValueChange = { secretKey = it },
                placeholder = { Text("Enter secret key") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BinanceYellow,
                    unfocusedBorderColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = BinanceYellow
                )
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(14.dp))
                    .background(CardDark).border(1.dp, GlassBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Text("Required Permissions", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                    PermLine("Read account balances")
                    PermLine("View transaction history")
                    PermLine("Spot trading (optional)")
                    Spacer(Modifier.height(8.dp))
                    Text("⚠ Never enable withdrawals permission", color = Warning, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth().height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(BinanceYellow)
                    .clickable {
                        connected = true
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Connect Binance", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PermLine(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("•", color = Primary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(text, color = TextSecondary, fontSize = 12.sp)
    }
    Spacer(Modifier.height(4.dp))
}