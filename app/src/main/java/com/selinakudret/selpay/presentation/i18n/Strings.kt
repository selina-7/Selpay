package com.selinakudret.selpay.presentation.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

enum class Language(val code: String, val displayName: String, val isRtl: Boolean) {
    EN("en", "English", false),
    TR("tr", "Türkçe", false),
    AR("ar", "العربية", true)
}

enum class Currency(val code: String, val displayName: String, val symbol: String, val country: String) {
    KWD("KWD", "Kuwaiti Dinar", "د.ك", "Kuwait"),
    BHD("BHD", "Bahraini Dinar", ".د.ب", "Bahrain"),
    AED("AED", "UAE Dirham", "د.إ", "UAE"),
    SAR("SAR", "Saudi Riyal", "﷼", "Saudi Arabia"),
    QAR("QAR", "Qatari Riyal", "﷼", "Qatar"),
    OMR("OMR", "Omani Rial", "ر.ع.", "Oman")
}

val LocalLanguage = compositionLocalOf<MutableState<Language>> {
    error("Language not provided")
}

val LocalCurrency = compositionLocalOf<MutableState<Currency>> {
    error("Currency not provided")
}

@Composable
fun LanguageProvider(content: @Composable () -> Unit) {
    val lang = remember { mutableStateOf(Language.EN) }
    val curr = remember { mutableStateOf(Currency.KWD) }
    CompositionLocalProvider(
        LocalLanguage provides lang,
        LocalCurrency provides curr
    ) {
        content()
    }
}

object S {
    @Composable
    fun get(en: String, tr: String, ar: String): String {
        val lang = LocalLanguage.current.value
        return when (lang) {
            Language.EN -> en
            Language.TR -> tr
            Language.AR -> ar
        }
    }
}