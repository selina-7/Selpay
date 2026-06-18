package com.selinakudret.selpay.presentation.navigation

sealed class BottomNavItem(val route: String, val icon: String, val label: String) {
    object Home : BottomNavItem("dashboard", "home", "Home")
    object Crypto : BottomNavItem("crypto", "diamond", "Crypto")
    object Cards : BottomNavItem("cards", "card", "Cards")
    object Insights : BottomNavItem("insights", "chart", "Insights")
}