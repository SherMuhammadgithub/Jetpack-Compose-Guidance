package com.example.googledesign.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// ============================================================
// WHAT IS THIS FILE?
// This is the MAIN THEME of your app. It combines:
//   1. Colors (from Color.kt)
//   2. Typography (from Type.kt)
// ...into a single MaterialTheme that wraps your whole app.
//
// HOW IT WORKS:
//   - Your MainActivity calls: GoogleDesignTheme { ... }
//   - This function checks if the phone is in dark mode
//   - It picks the right color set (light or dark)
//   - It wraps everything in MaterialTheme(...)
//   - Now ANY composable inside can access the theme colors/fonts:
//       MaterialTheme.colorScheme.primary  → gives you the blue
//       MaterialTheme.typography.bodyLarge  → gives you body text style
//
// DYNAMIC COLOR (Android 12+):
//   On Android 12+, "Dynamic Color" lets your app automatically
//   match the user's wallpaper colors. Super cool! We enable this
//   by default but fall back to our custom colors on older phones.
// ============================================================

// Light color scheme — maps our Color.kt values to Material roles
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
)

// Dark color scheme — same structure, darker colors
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
)

/**
 * The main theme composable for GoogleDesign app.
 *
 * @param darkTheme Whether to use dark theme. Defaults to system setting.
 * @param dynamicColor Whether to use Android 12+ dynamic wallpaper colors.
 * @param content The composable content to be themed.
 */
@Composable
fun GoogleDesignTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Step 1: Pick the right color scheme
    val colorScheme = when {
        // If dynamic color is enabled AND device is Android 12+ (API 31),
        // use wallpaper-based colors
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Otherwise, use our custom colors from Color.kt
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Step 2: Apply the theme to all child composables
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,     // From Type.kt
        content = content
    )
}
