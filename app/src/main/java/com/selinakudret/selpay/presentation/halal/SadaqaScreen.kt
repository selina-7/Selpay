package com.selinakudret.selpay.presentation.halal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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

data class Charity(val name: String, val description: String, val raised: Float, val goal: Float)

@Composable
fun SadaqaScreen(navController: NavController) {
    var roundUpEnabled by remember { mutableStateOf(true) }
    val halalGreen = Color(0xFF14B886)
    val totalGiven = 47.30f
    val thisMonth = 12.40f

    val charities = listOf(
        Charity("Kuwait Red Crescent", "Emergency relief for refugees", 850000f, 1000000f),
        Charity("Direct Aid Society", "Building wells in Africa", 423000f, 500000f),
        Charity("Orphan Sponsorship", "Monthly sponsor for orphans", 230000f, 300000f),
        Charity("Masjid Construction", "Building a mosque in Bangladesh", 145000f, 250000f)
    )

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
            Text("Sadaqa Tracker", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
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
                    Text("Total Sadaqa Given", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "KWD ${"%,.2f".format(totalGiven)}",
                        color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("This month: KWD ${"%,.2f".format(thisMonth)}", color = Color.White, fontSize = 12.sp)
                    Spacer(Modifier.height(14.dp))
                    Text(
                        "\"The believer's shade on the Day of Resurrection will be his charity.\"",
                        color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .border(1.dp, halalGreen.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Round-up Sadaqa", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Round purchases to nearest KWD,", color = TextSecondary, fontSize = 11.sp)
                            Text("donate the difference automatically", color = TextSecondary, fontSize = 11.sp)
                        }
                        Switch(
                            checked = roundUpEnabled,
                            onCheckedChange = { roundUpEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = halalGreen,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = CardElevated
                            )
                        )
                    }
                    if (roundUpEnabled) {
                        Spacer(Modifier.height(12.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(GlassBorder))
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "This month: KWD 12.40 donated from 23 purchases",
                            color = halalGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Active Charities", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))

            charities.forEach { c ->
                CharityCard(c, halalGreen)
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth().height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(halalGreen)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text("Browse All Charities", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun CharityCard(charity: Charity, accent: Color) {
    val progress = charity.raised / charity.goal
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(1.dp, GlassBorder, RoundedCornerShape(18.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(charity.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(charity.description, color = TextSecondary, fontSize = 11.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(accent.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Donate", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = accent,
                trackColor = accent.copy(alpha = 0.15f)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "KWD ${(charity.raised / 1000).toInt()}K of ${(charity.goal / 1000).toInt()}K raised",
                color = TextSecondary, fontSize = 10.sp
            )
        }
    }
}