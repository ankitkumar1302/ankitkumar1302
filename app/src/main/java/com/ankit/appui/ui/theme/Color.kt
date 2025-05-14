package com.ankit.appui.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Custom Colors for Twitch Insights UI
val AlmostBlack = Color(0xFF0C0C0C) // Darker background for better contrast
val DarkGray = Color(0xFF181818)    // Darker card background for better contrast
val DarkCardColor = Color(0xFF202020) // Darker card backgrounds
val LightGray = Color(0xFFCCCCCC)    // Lighter for better contrast
val OffWhite = Color(0xFFF5F5F5)    // Whiter text color for better contrast

// Enhanced colors for better accessibility (WCAG AA compliant)
val TwitchPurple = Color(0xFF8A3FFC) // Standard purple accent (stronger contrast)
val TwitchBlue = Color(0xFF42BFFF)   // Brightened blue for better contrast
val TwitchPink = Color(0xFFFF69B4)   // Brightened pink for better contrast
val PositiveGreen = Color(0xFF42E080) // For positive change indicator
val NegativeRed = Color(0xFFFF5757) // Brightened for negative trends

val LightPurple = Color(0xFFAC6FFF) // Lightened for better contrast
val DarkPurple = Color(0xFF6929C4)   // Darkened for better contrast

val PurpleTransparent = TwitchPurple.copy(alpha = 0.5f)
val TransparentPurple = TwitchPurple.copy(alpha = 0.0f)