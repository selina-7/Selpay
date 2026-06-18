package com.selinakudret.selpay.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.presentation.i18n.Currency
import com.selinakudret.selpay.presentation.i18n.Language
import com.selinakudret.selpay.presentation.i18n.LocalCurrency
import com.selinakudret.selpay.presentation.i18n.LocalLanguage
import com.selinakudret.selpay.presentation.theme.LocalHalalMode
import com.selinakudret.selpay.presentation.theme.LocalThemeState
import com.selinakudret.selpay.ui.theme.*

@Composable
fun SettingsScreen(navController: NavController) {
    val darkModeState = LocalThemeState.current
    var darkMode by darkModeState

    val halalState = LocalHalalMode.current
    var halalMode by halalState

    val langState = LocalLanguage.current
    var currentLang by langState

    val currState = LocalCurrency.current
    var currentCurrency by currState

    var notifications by remember { mutableStateOf(true) }
    var biometricLogin by remember { mutableStateOf(true) }
    var roundUp by remember { mutableStateOf(true) }

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
            ) {
                Text("‹", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(16.dp))
            Text("Settings", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Black)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionLabel("Preferences")
            SettingsGroup {
                ToggleRow("Dark Mode", "Easier on the eyes", darkMode) { darkMode = it }
                Divider()
                LanguageRow(currentLang) { currentLang = it }
                Divider()
                CurrencyRow(currentCurrency) { currentCurrency = it }
                Divider()
                NavRow("Region", currentCurrency.country) { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Security")
            SettingsGroup {
                ToggleRow("Biometric Login", "Use fingerprint to sign in", biometricLogin) { biometricLogin = it }
                Divider()
                NavRow("Change Password", "Last changed 2 months ago") { }
                Divider()
                NavRow("Two-Factor Authentication", "Active via SMS") { }
                Divider()
                NavRow("Identity Verification", "Verified") { }
                Divider()
                NavRow("Trusted Devices", "3 devices") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Account")
            SettingsGroup {
                NavRow("Personal Information", "Name, DOB, Civil ID") { navController.navigate("profile") }
                Divider()
                NavRow("Linked Accounts", "Binance, 2 bank accounts") { navController.navigate("binance_connect") }
                Divider()
                NavRow("Card Limits", "Daily, monthly, ATM") { navController.navigate("card_limits") }
                Divider()
                NavRow("Statements & Documents", "Download PDF") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Notifications")
            SettingsGroup {
                ToggleRow("Push Notifications", "Transactions, security alerts", notifications) { notifications = it }
                Divider()
                NavRow("Email Preferences", "Weekly summary on") { }
                Divider()
                NavRow("SMS Alerts", "Critical only") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Smart Features")
            SettingsGroup {
                ToggleRow("Round-up Savings", "Round purchases to nearest", roundUp) { roundUp = it }
                Divider()
                NavRow("AI Assistant", "Powered by SelPay AI") { navController.navigate("assistant") }
                Divider()
                NavRow("Spending Insights", "Weekly digest") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Islamic Banking")
            SettingsGroup {
                ToggleRow("Halal Mode", "Sharia-compliant banking", halalMode) { halalMode = it }
                Divider()
                NavRow("Zakat Calculator", "Calculate your annual Zakat") { navController.navigate("zakat") }
                Divider()
                NavRow("Sadaqa Tracker", "Automatic charity donations") { navController.navigate("sadaqa") }
                Divider()
                NavRow("Prayer Times", "Sync with payment reminders") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Family")
            SettingsGroup {
                NavRow("Family Banking", "Manage family members") { navController.navigate("family") }
                Divider()
                NavRow("Parental Controls", "Spending limits & restrictions") { }
                Divider()
                NavRow("Chore & Rewards", "Set up tasks for kids") { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Support")
            SettingsGroup {
                NavRow("Help Center", "FAQ, contact us") { }
                Divider()
                NavRow("Report a Problem", "") { }
                Divider()
                NavRow("Terms of Service", "") { }
                Divider()
                NavRow("Privacy Policy", "") { }
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(RedNegative.copy(alpha = 0.12f))
                    .border(1.dp, RedNegative.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Sign Out", color = RedNegative, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(Modifier.height(20.dp))
            Text(
                "SelPay v1.0.0",
                color = TextMuted, fontSize = 11.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text.uppercase(),
        color = TextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsGroup(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .border(1.dp, GlassBorder, RoundedCornerShape(18.dp))
    ) {
        Column { content() }
    }
}

@Composable
private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, color = TextSecondary, fontSize = 11.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = CardElevated
            )
        )
    }
}

@Composable
private fun NavRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, color = TextSecondary, fontSize = 11.sp)
            }
        }
        Text("›", color = TextSecondary, fontSize = 20.sp)
    }
}

@Composable
private fun LanguageRow(current: Language, onSelect: (Language) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text("Language", color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Language.values().forEach { lang ->
                val isSel = lang == current
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSel) Primary else CardElevated)
                        .clickable { onSelect(lang) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        lang.displayName,
                        color = if (isSel) Color.White else TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyRow(current: Currency, onSelect: (Currency) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Currency", color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text("${current.code} - ${current.displayName}", color = TextSecondary, fontSize = 11.sp)
            }
            Text(if (expanded) "▴" else "▾", color = TextSecondary, fontSize = 14.sp)
        }
        if (expanded) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Currency.values().forEach { c ->
                    val isSel = c == current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSel) Primary.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable {
                                onSelect(c)
                                expanded = false
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp).clip(CircleShape)
                                .background(if (isSel) Primary else CardElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                c.code.first().toString(),
                                color = if (isSel) Color.White else TextPrimary,
                                fontWeight = FontWeight.Black, fontSize = 12.sp
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(c.code, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(c.country, color = TextSecondary, fontSize = 11.sp)
                        }
                        if (isSel) {
                            Text("✓", color = Primary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(0.5.dp)
            .background(GlassBorder)
    )
}