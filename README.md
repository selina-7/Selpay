# SelPay — FinTech Banking App for the Middle East 🌍

> *Where banking meets belief.*

SelPay is a concept fintech banking application designed for the GCC region, built entirely with **Kotlin** and **Jetpack Compose**. It reimagines mobile banking around the needs of Middle Eastern users — featuring Halal banking tools, family accounts, multi-currency support, and a trilingual AI assistant — wrapped in a premium, animation-rich interface.

This is a solo portfolio project showcasing modern Android development practices, clean architecture, and thoughtful UX design.

---

## ✨ Features

- 🌙 **Halal Banking Mode** — Built-in Zakat calculator, Sadaqa tracker, and Nisab-based calculations for Sharia-conscious users
- 👨‍👩‍👧 **Family Banking** — Kid accounts, chore-based rewards, and parental spending controls
- 🎤 **AI Assistant** — Conversational assistant with English, Arabic, and Turkish support
- 💱 **GCC Multi-Currency** — Native support for KWD, BHD, AED, SAR, QAR, and OMR
- 💳 **Smart Cards** — Apple Wallet–style card management with freeze/unfreeze, limits, and details
- 📊 **Insights & Analytics** — Net worth tracking, cash flow visualization, spending breakdowns, and smart suggestions
- 🪙 **Crypto Portfolio** — Live ticker, portfolio charts, holdings, and top movers
- 🎨 **Premium UX** — Haptic feedback, fluid animations, glassmorphic design, and a dynamic-island–style notification system
- 🌐 **Full Localization** — English, Arabic (with RTL), and Turkish

---

## 🛠️ Tech Stack

| Layer | Technologies |
|-------|-------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose, Material Design 3 |
| **Architecture** | MVVM, Clean Architecture principles |
| **Dependency Injection** | Hilt |
| **Navigation** | Jetpack Navigation Compose |
| **Design** | Custom animations, glassmorphism, haptics |

---

## 🏗️ Architecture

SelPay follows the **MVVM pattern** with a feature-based package structure, keeping UI, state, and logic cleanly separated:

```
presentation/
├── actions/        # Send, Receive, Swap, Scan flows
├── assistant/      # Trilingual AI assistant
├── cards/          # Card management (Wallet-style)
├── crypto/         # Crypto portfolio & Binance connect
├── dashboard/      # Home dashboard with hero balance
├── effects/        # Reusable animation & UI effect components
├── family/         # Family banking & kid accounts
├── halal/          # Zakat & Sadaqa tools
├── insights/       # Analytics & net worth
├── navigation/     # NavGraph, bottom navigation, scaffold
├── onboarding/     # Splash & onboarding flow
├── profile/        # Editable user profile
├── settings/       # Theme, language, currency, Halal mode
└── transactions/   # Transaction history with search & filters
```

The `effects/` package contains a custom toolkit of reusable Compose components — animated counters, particle bursts, confetti, glass cards, gradient meshes, skeleton loaders, pull-to-refresh, and a dynamic-island–style notification system.

---

## 🌍 Built for the GCC

SelPay was designed with the Gulf banking ecosystem in mind. The concept draws on hands-on experience with payment systems and financial messaging standards (EMV, ISO 8583) gained during a fintech internship in Bahrain, translating that domain understanding into a user-facing product vision for the region.

---

## 📱 Getting Started

```bash
# Clone the repository
git clone https://github.com/selina-7/Selpay.git

# Open in Android Studio (Hedgehog or newer)
# Sync Gradle and run on an emulator or device (minSdk 26)
```

**Requirements:** Android Studio, JDK 21, Android SDK 36

---

## 👩‍💻 About the Developer

**Selina Kudret** — Software Engineer specializing in Android & FinTech development.

- 🎓 BSc Software Engineering
- 💼 FinTech internship experience (EMV, ISO 8583)
- 🌐 Trilingual: Turkish, English, Arabic
- 🔗 [LinkedIn](https://linkedin.com/in/selina-kudret) · [GitHub](https://github.com/selina-7)

---

*SelPay is a portfolio project built to demonstrate modern Android development and product design. It uses sample data and is not a production financial application.*
