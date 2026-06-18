package com.selinakudret.selpay.presentation.assistant

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.selinakudret.selpay.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

data class ChatMessage(val text: String, val fromUser: Boolean, val isVoice: Boolean = false)

@Composable
fun AssistantScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }
    var selectedLang by remember { mutableStateOf("en") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hi Selina! I'm your SelPay assistant. You can type or tap the mic to talk.", false)
        )
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Pulse animation for mic
    val infinite = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "scale"
    )

    // Speech recognizer launcher
    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText: String? = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                messages.add(ChatMessage(spokenText, true, isVoice = true))
                scope.launch {
                    delay(700)
                    messages.add(ChatMessage(generateAnswer(spokenText, selectedLang), false))
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
        }
    }

    val suggestions = when (selectedLang) {
        "tr" -> listOf("Bu ay ne kadar harcadım?", "Ali'ye 50 KWD gönder", "Bakiyem ne kadar?", "Faturalarımı göster")
        "ar" -> listOf("كم أنفقت هذا الشهر؟", "ما هو رصيدي؟", "أرسل 50 دينار إلى علي", "أظهر فواتيري")
        else -> listOf("How much did I spend this month?", "Send 50 KWD to Ali", "What's my balance?", "Show my bills")
    }

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
                    .size(40.dp).clip(CircleShape).background(CardDark)
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) { Text("‹", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(40.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue))),
                contentAlignment = Alignment.Center
            ) { Text("AI", color = Color.White, fontWeight = FontWeight.Black, fontSize = 12.sp) }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("SelPay Assistant", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(GreenPositive))
                    Spacer(Modifier.width(4.dp))
                    Text("Voice ready", color = GreenPositive, fontSize = 11.sp)
                }
            }
        }

        // Language switcher
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LangChip("EN", "en", selectedLang) { selectedLang = it }
            LangChip("TR", "tr", selectedLang) { selectedLang = it }
            LangChip("AR", "ar", selectedLang) { selectedLang = it }
        }

        Spacer(Modifier.height(12.dp))

        // Messages
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg -> MessageBubble(msg) }
            if (messages.size <= 1) {
                item {
                    Spacer(Modifier.height(12.dp))
                    Text("Try saying:", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    suggestions.forEach { s ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(CardDark)
                                .border(1.dp, Primary.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                                .clickable {
                                    messages.add(ChatMessage(s, true))
                                    scope.launch {
                                        delay(700)
                                        messages.add(ChatMessage(generateAnswer(s, selectedLang), false))
                                        listState.animateScrollToItem(messages.size - 1)
                                    }
                                }
                                .padding(14.dp)
                        ) { Text(s, color = TextPrimary, fontSize = 13.sp) }
                    }
                }
            }
        }

        // Input row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = {
                    Text(when (selectedLang) {
                        "tr" -> "Sorun veya konuş..."
                        "ar" -> "اكتب أو تحدث..."
                        else -> "Ask or speak..."
                    })
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = Primary
                )
            )
            Spacer(Modifier.width(8.dp))

            // Mic button (pulsing)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(AccentRose, AccentPurple)))
                    .clickable {
                        val locale = when (selectedLang) {
                            "tr" -> "tr-TR"
                            "ar" -> "ar-SA"
                            else -> "en-US"
                        }
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, when (selectedLang) {
                                "tr" -> "Söyleyin..."
                                "ar" -> "تحدث..."
                                else -> "Speak now..."
                            })
                        }
                        try {
                            voiceLauncher.launch(intent)
                        } catch (_: Exception) {}
                    },
                contentAlignment = Alignment.Center
            ) { Text("◉", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold) }

            Spacer(Modifier.width(8.dp))

            // Send button
            Box(
                modifier = Modifier
                    .size(48.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .clickable {
                        if (input.isNotBlank()) {
                            val msg = input
                            messages.add(ChatMessage(msg, true))
                            input = ""
                            scope.launch {
                                delay(800)
                                messages.add(ChatMessage(generateAnswer(msg, selectedLang), false))
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) { Text("→", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
private fun LangChip(label: String, code: String, selected: String, onSelect: (String) -> Unit) {
    val isSel = code == selected
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSel) Primary else CardDark)
            .border(1.dp, if (isSel) Primary else GlassBorder, RoundedCornerShape(12.dp))
            .clickable { onSelect(code) }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(label, color = if (isSel) Color.White else TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MessageBubble(msg: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.fromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!msg.fromUser) {
            Box(
                modifier = Modifier
                    .size(32.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(AccentPurple, AccentBlue))),
                contentAlignment = Alignment.Center
            ) { Text("AI", color = Color.White, fontWeight = FontWeight.Black, fontSize = 10.sp) }
            Spacer(Modifier.width(8.dp))
        }
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(
                    topStart = 18.dp, topEnd = 18.dp,
                    bottomEnd = if (msg.fromUser) 4.dp else 18.dp,
                    bottomStart = if (msg.fromUser) 18.dp else 4.dp
                ))
                .background(if (msg.fromUser) Primary else CardDark)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                if (msg.isVoice) {
                    Text("Voice", color = Color.White.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(2.dp))
                }
                Text(msg.text, color = if (msg.fromUser) Color.White else TextPrimary, fontSize = 13.sp)
            }
        }
    }
}

private fun generateAnswer(question: String, lang: String): String {
    val q = question.lowercase(Locale.ROOT)
    return when {
        // Send money intent (voice command)
        ("send" in q || "transfer" in q || "gönder" in q || "أرسل" in q) && q.any { it.isDigit() } -> {
            when (lang) {
                "tr" -> "Tabii. ${q.filter { it.isDigit() }} KWD transferini başlatmak için onay ekranını açıyorum."
                "ar" -> "بالتأكيد. سأفتح شاشة التأكيد لإرسال المبلغ."
                else -> "Sure! Opening confirmation screen to send the amount."
            }
        }
        "balance" in q || "bakiye" in q || "رصيد" in q -> when (lang) {
            "tr" -> "Toplam bakiyeniz KWD 4,250.00. Kullanılabilir: KWD 4,250.00."
            "ar" -> "رصيدك الإجمالي 4,250.00 دينار كويتي."
            else -> "Your total balance is KWD 4,250.00. Available: KWD 4,250.00."
        }
        "save" in q || "tasarruf" in q || "وفر" in q -> when (lang) {
            "tr" -> "Bu ay KWD 120 tasarruf ettin — gelirinin %18'i. Geçen aydan %25 ilerideisin!"
            "ar" -> "وفرت 120 دينار هذا الشهر، أي 18% من دخلك. أداء ممتاز!"
            else -> "You saved KWD 120 this month — 18% of your income. You're 25% ahead of last month!"
        }
        "food" in q || "yemek" in q || "طعام" in q -> when (lang) {
            "tr" -> "Bu ay yemeğe KWD 185 harcadın, 23 işlemde. En çok Carrefour'da (KWD 62)."
            "ar" -> "أنفقت 185 دينار على الطعام هذا الشهر في 23 معاملة."
            else -> "You spent KWD 185 on food this month across 23 transactions. Top merchant: Carrefour (KWD 62)."
        }
        "bill" in q || "fatura" in q || "فاتورة" in q -> when (lang) {
            "tr" -> "Sıradaki fatura: Elektrik, 5 gün sonra, tahmini KWD 32. Otomatik ödeme açık."
            "ar" -> "الفاتورة القادمة: الكهرباء بعد 5 أيام، تقريباً 32 دينار."
            else -> "Next bill: Electricity due in 5 days, estimated KWD 32. Auto-pay is on."
        }
        "spend" in q || "harca" in q || "أنفق" in q -> when (lang) {
            "tr" -> "Bu ay toplam KWD 482 harcadın. Geçen aya göre %23 daha az!"
            "ar" -> "أنفقت 482 دينار هذا الشهر، أقل بنسبة 23% من الشهر الماضي."
            else -> "You spent KWD 482 this month — 23% less than last month!"
        }
        else -> when (lang) {
            "tr" -> "Hâlâ öğreniyorum! Bakiye, harcamalar, transferler veya faturalar hakkında soru sorabilirsin."
            "ar" -> "ما زلت أتعلم! اسألني عن رصيدك أو مصاريفك أو فواتيرك."
            else -> "I'm still learning! Try asking about your balance, spending, transfers, or bills."
        }
    }
}