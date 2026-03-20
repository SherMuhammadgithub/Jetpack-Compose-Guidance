package com.example.googledesign.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.googledesign.R

// ============================================================
// WHAT IS THIS FILE?
// This defines your app's TYPOGRAPHY — all the text styles.
//
// Material 3 has a "type scale" with these levels:
//
//   Display  (Large, Medium, Small)  → Hero text, very large
//   Headline (Large, Medium, Small)  → Page titles
//   Title    (Large, Medium, Small)  → Section titles, app bars
//   Body     (Large, Medium, Small)  → Paragraph text, descriptions
//   Label    (Large, Medium, Small)  → Buttons, captions, tags
//
// FONT FAMILY:
//   We use the system default font (sans-serif) here.
//   To use a custom font (like Google Sans or Poppins):
//     1. Download the .ttf files
//     2. Put them in app/src/main/res/font/
//     3. Create a FontFamily referencing them (shown below commented)
//
// WHAT IS "sp"?
//   sp = "Scale-independent Pixels". It's like dp but also
//   respects the user's font size preference in phone Settings.
//   ALWAYS use sp for text sizes so accessibility works properly.
//
// WHAT IS "LineHeight"?
//   The vertical space a line of text occupies. A lineHeight of
//   28.sp means each line takes 28sp of vertical space. This
//   controls the spacing between lines in a paragraph.
//
// WHAT IS "letterSpacing"?
//   The horizontal space between each character. 0.sp = normal.
//   Negative values = tighter, positive = more spread out.
//   Typically: headlines use tighter spacing, body uses normal.
// ============================================================

// --- FONT FAMILY SETUP ---
//
// Poppins font from Google Fonts
// Files must be in: app/src/main/res/font/
//   poppins_regular.ttf   (FontWeight.Normal = 400)
//   poppins_medium.ttf    (FontWeight.Medium = 500)
//   poppins_semibold.ttf  (FontWeight.SemiBold = 600)
//   poppins_bold.ttf      (FontWeight.Bold = 700)
//
// Each Font() maps a .ttf file to a weight.
// When you use FontWeight.Bold in your code,
// Compose picks poppins_bold.ttf automatically!
val AppFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

// --- THE TYPE SCALE ---
// This is where you define every text style your app will use.

val AppTypography = Typography(

    // DISPLAY — Very large, attention-grabbing text
    // Use for: hero sections, splash screens, big numbers
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp    // Slightly tighter for large text
    ),
    displayMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // HEADLINE — Page-level titles
    // Use for: screen titles, dialog titles, large section headers
    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // TITLE — Smaller titles
    // Use for: app bar title, card titles, list section headers
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,    // Medium weight = slightly bolder
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // BODY — Main content text
    // Use for: paragraphs, descriptions, form fields, list items
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // LABEL — Small UI text
    // Use for: buttons, chips, tabs, captions, badges
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
