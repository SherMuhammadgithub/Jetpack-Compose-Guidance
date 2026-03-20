package com.example.googledesign.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================================
// WHAT IS THIS FILE?
// This defines all the colors your app uses.
//
// In Material 3, colors are organized by "roles":
//   - Primary    → Your main brand color (buttons, active states)
//   - Secondary  → Accent color (chips, smaller highlights)
//   - Tertiary   → Extra color for variety
//   - Background → The screen background
//   - Surface    → Cards, sheets, dialogs sit on "surfaces"
//   - Error      → Red-ish for errors & warnings
//
// Each role has variants:
//   - primary         → The actual color
//   - onPrimary       → Text/icon color ON TOP of primary
//   - primaryContainer → A softer version of primary (for backgrounds)
//   - onPrimaryContainer → Text color on that soft background
// ============================================================

// ---------- LIGHT THEME COLORS ----------

val md_theme_light_primary = Color(0xFF1A73E8)             // Google Blue
val md_theme_light_onPrimary = Color(0xFFFFFFFF)            // White text on blue
val md_theme_light_primaryContainer = Color(0xFFD3E3FD)     // Light blue container
val md_theme_light_onPrimaryContainer = Color(0xFF041E49)   // Dark text on light blue

val md_theme_light_secondary = Color(0xFF5F6368)            // Google Grey
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE8EAED)
val md_theme_light_onSecondaryContainer = Color(0xFF1F1F1F)

val md_theme_light_tertiary = Color(0xFF188038)             // Google Green
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFCEEAD6)
val md_theme_light_onTertiaryContainer = Color(0xFF0D3B1E)

val md_theme_light_error = Color(0xFFD93025)                // Google Red
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFFCE8E6)
val md_theme_light_onErrorContainer = Color(0xFF410E0B)

val md_theme_light_background = Color(0xFFFFFBFE)           // Near-white
val md_theme_light_onBackground = Color(0xFF1C1B1F)         // Dark text
val md_theme_light_surface = Color(0xFFFFFBFE)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_surfaceVariant = Color(0xFFF1F3F4)
val md_theme_light_onSurfaceVariant = Color(0xFF444746)
val md_theme_light_outline = Color(0xFF747775)

// ---------- DARK THEME COLORS ----------

val md_theme_dark_primary = Color(0xFF8AB4F8)               // Lighter blue for dark bg
val md_theme_dark_onPrimary = Color(0xFF062E6F)
val md_theme_dark_primaryContainer = Color(0xFF0842A0)
val md_theme_dark_onPrimaryContainer = Color(0xFFD3E3FD)

val md_theme_dark_secondary = Color(0xFFC4C7C5)
val md_theme_dark_onSecondary = Color(0xFF2D312F)
val md_theme_dark_secondaryContainer = Color(0xFF444746)
val md_theme_dark_onSecondaryContainer = Color(0xFFE8EAED)

val md_theme_dark_tertiary = Color(0xFF81C995)              // Lighter green for dark bg
val md_theme_dark_onTertiary = Color(0xFF0D3B1E)
val md_theme_dark_tertiaryContainer = Color(0xFF1E5631)
val md_theme_dark_onTertiaryContainer = Color(0xFFCEEAD6)

val md_theme_dark_error = Color(0xFFF28B82)                 // Softer red for dark bg
val md_theme_dark_onError = Color(0xFF601410)
val md_theme_dark_errorContainer = Color(0xFF8C1D18)
val md_theme_dark_onErrorContainer = Color(0xFFFCE8E6)

val md_theme_dark_background = Color(0xFF1C1B1F)            // Dark background
val md_theme_dark_onBackground = Color(0xFFE6E1E5)          // Light text
val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSurface = Color(0xFFE6E1E5)
val md_theme_dark_surfaceVariant = Color(0xFF444746)
val md_theme_dark_onSurfaceVariant = Color(0xFFC4C7C5)
val md_theme_dark_outline = Color(0xFF8E918F)
