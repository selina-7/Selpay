package com.selinakudret.selpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.selinakudret.selpay.presentation.i18n.LanguageProvider
import com.selinakudret.selpay.presentation.i18n.LocalLanguage
import com.selinakudret.selpay.presentation.navigation.NavGraph
import com.selinakudret.selpay.presentation.theme.HalalModeProvider
import com.selinakudret.selpay.presentation.theme.ThemeProvider
import com.selinakudret.selpay.ui.theme.SelPayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProvider {
                HalalModeProvider {
                    LanguageProvider {
                        val lang by LocalLanguage.current
                        val layoutDirection = if (lang.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
                        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                            SelPayTheme {
                                NavGraph()
                            }
                        }
                    }
                }
            }
        }
    }
}