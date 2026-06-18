package com.selinakudret.selpay.presentation.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.effects.HapticType
import com.selinakudret.selpay.presentation.effects.rememberHaptic
import com.selinakudret.selpay.presentation.effects.springClickable
import com.selinakudret.selpay.ui.theme.*

@Composable
fun TopBar(title: String, navController: NavController) {
    val haptic = rememberHaptic()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
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
        Text(title, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun fieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Primary,
    focusedLabelColor = Primary,
    unfocusedBorderColor = TextMuted,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary,
    cursorColor = Primary,
    focusedPlaceholderColor = TextMuted,
    unfocusedPlaceholderColor = TextMuted
)