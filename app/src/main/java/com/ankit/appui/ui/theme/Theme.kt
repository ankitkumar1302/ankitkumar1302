package com.ankit.appui.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = TwitchPurple,
    secondary = TwitchBlue,
    tertiary = TwitchPink,
    background = AlmostBlack,
    surface = DarkGray,
    onPrimary = OffWhite,
    onSecondary = OffWhite,
    onTertiary = OffWhite,
    onBackground = OffWhite,
    onSurface = OffWhite,
    error = androidx.compose.ui.graphics.Color.Red,
    onError = OffWhite
)

// For this app, we only want a dark theme based on the design.
// We can ignore lightColorScheme for now or make it similar to dark if needed.
private val LightColorScheme = lightColorScheme(
    primary = TwitchPurple,
    secondary = TwitchBlue,
    tertiary = TwitchPink,
    background = AlmostBlack, // Forcing dark theme
    surface = DarkGray,      // Forcing dark theme
    onPrimary = OffWhite,
    onSecondary = OffWhite,
    onTertiary = OffWhite,
    onBackground = OffWhite,
    onSurface = OffWhite,
    error = androidx.compose.ui.graphics.Color.Red,
    onError = OffWhite

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AppUITheme(
    darkTheme: Boolean = true, // Force dark theme as per design
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color to stick to custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // Default to DarkColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            
            // Make status bar fully transparent 
            window.statusBarColor = Color.Transparent.toArgb()
            
            // Set navigation bar to match our dark gray color
            window.navigationBarColor = DarkGray.toArgb()
            
            // Use light icons for both status and navigation bars
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false  // Use light (white) icons on dark background
                isAppearanceLightNavigationBars = false  // Use light (white) icons on dark background
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = CardShapes, // Using CardShapes with 16.dp medium radius
        content = content
    )
}