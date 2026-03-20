# Jetpack Compose Guide

A hands-on learning app built with **Jetpack Compose** and **Material 3**, designed to teach Compose fundamentals through interactive examples. Each concept is demonstrated with working code, inline comments, and live UI you can interact with.

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Kotlin | 2.1.20 | Programming language |
| Jetpack Compose | BOM 2025.04.01 | Declarative UI framework |
| Material 3 | Latest (via BOM) | Design system & components |
| Navigation Compose | 2.9.0 | Screen navigation |
| Android Gradle Plugin | 9.0.1 | Build system |
| Min SDK | 24 (Android 7.0) | Minimum supported version |
| Target SDK | 36 (Android 15) | Target version |

## Project Structure

```
app/src/main/java/com/example/googledesign/
├── MainActivity.kt                  # Entry point — launches MainApp
├── MainApp.kt                       # Navigation setup (NavHost + routes)
│
├── ui/theme/                        # Material 3 Theme
│   ├── Color.kt                     # Light & Dark color palettes
│   ├── Type.kt                      # Typography (Poppins font, type scale)
│   └── Theme.kt                     # Theme composable (ties colors + typography)
│
└── learn/                           # Learning modules
    ├── Level1Basics.kt              # @Composable, Text, Button, Column, Row, Box
    ├── Level2Styling.kt             # Colors, Shapes, Typography, Spacing
    ├── Level3Interactive.kt         # State, TextField, Clicks, Toggles
    ├── Level4Lists.kt              # LazyColumn, LazyRow, Cards, Navigation
    └── Level5Patterns.kt           # Scaffold, ViewModel pattern, Recomposition

app/src/main/res/
├── font/                            # Poppins font files (.ttf)
├── layout/                          # Legacy XML layout (unused)
├── values/                          # Colors, strings, themes (XML)
└── drawable/                        # App icons and drawables
```

## What's Covered

### Level 1 — The Basics
- What is `@Composable` and how it replaces XML layouts
- `Text`, `Button`, `Icon` — the fundamental building blocks
- `Modifier` — the chain system for styling (size, padding, background)
- `Column`, `Row`, `Box` — the three layout containers
- Combined example: User profile card

### Level 2 — Styling & Appearance
- 6 ways to define colors (hex, named, RGBA, theme, copy, transparent)
- Backgrounds and gradients (horizontal, vertical, multi-color)
- Shapes: `RoundedCornerShape`, `CircleShape`, `CutCornerShape`
- Borders, shadows, `clip()`, and `Card` component
- Typography: font weights, text alignment, decoration, overflow
- `buildAnnotatedString` — multiple styles in one text
- Padding vs Spacer vs Arrangement — when to use which

### Level 3 — Making Things Interactive
- `remember` + `mutableStateOf` — how state drives UI updates
- Counter, dynamic greeting, toggle (show/hide), color picker
- `TextField` and `OutlinedTextField` — filled vs outlined
- Search field with icons, password field with visibility toggle
- Keyboard types (`Email`, `Phone`, `Number`) and IME actions
- Input validation with error states
- All 5 Material 3 button types
- `clickable` modifier, Switch, Checkbox, RadioButton, Slider
- Combined example: Mini login form

### Level 4 — Lists & Navigation
- `LazyColumn` vs `Column` with scroll — when to use which
- `items()`, `itemsIndexed()` — looping through data
- `LazyRow` — horizontal scrolling (stories, chips)
- `mutableStateListOf` — dynamic add/remove with auto UI updates
- Card types: `Card`, `ElevatedCard`, `OutlinedCard`
- `ListItem` — pre-built Material 3 list row component
- `NavigationBar` with tabs and screen switching
- `Scaffold` with `TopAppBar`, `BottomBar`, and `FloatingActionButton`
- Master-detail pattern (list to detail screen)

### Level 5 — Real App Patterns
- Settings screen pattern (sectioned list with icons)
- Shopping cart screen (list + bottom summary + empty state)
- Search screen (real-time filtering, install/open toggle)
- ViewModel pattern — state holder class for surviving configuration changes
- State hoisting — parent owns state, children receive it
- Recomposition — when and why Compose redraws
- `remember(key)` — recalculate only when dependencies change
- Common recomposition mistakes and how to avoid them

## Setup Guide

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17+
- Android SDK 36

### How Jetpack Compose Was Added

This project was originally a traditional View-based Android project (XML layouts + `AppCompatActivity`). Here's what was changed to enable Compose:

**1. Version Catalog (`gradle/libs.versions.toml`)**

Added Compose BOM, Material 3, and Navigation dependencies:
```toml
[versions]
kotlin = "2.1.20"
composeBom = "2025.04.01"
navigationCompose = "2.9.0"

[libraries]
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
# ... (see libs.versions.toml for full list)

[plugins]
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

**2. Root `build.gradle.kts`**

Registered the Compose compiler plugin:
```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

**3. App `build.gradle.kts`**

Applied the plugin and added dependencies:
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    // ...
}
```

**4. `MainActivity.kt`**

Changed from `AppCompatActivity` + XML to `ComponentActivity` + Compose:
```kotlin
// Before (XML)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }
}

// After (Compose)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContent {
            GoogleDesignTheme {
                MainApp()
            }
        }
    }
}
```

### Theme Setup

The app uses a custom Material 3 theme with:

- **Color scheme** — Google-inspired colors with light/dark variants and dynamic color support (Android 12+)
- **Typography** — Poppins font family with the full Material 3 type scale (Display, Headline, Title, Body, Label)
- **Dynamic theming** — Automatically adapts to the user's wallpaper on Android 12+

### Custom Font (Poppins)

Font files are placed in `app/src/main/res/font/`:
```
poppins_regular.ttf    → FontWeight.Normal (400)
poppins_medium.ttf     → FontWeight.Medium (500)
poppins_semibold.ttf   → FontWeight.SemiBold (600)
poppins_bold.ttf       → FontWeight.Bold (700)
```

Referenced in `Type.kt`:
```kotlin
val AppFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)
```

### Navigation

The app uses **Navigation Compose** with a simple route structure:

| Route | Screen |
|---|---|
| `home` | Landing page with level cards |
| `level/{levelNumber}` | Level detail screen (1-5) |

```kotlin
NavHost(navController, startDestination = "home") {
    composable("home") { HomeLandingPage(...) }
    composable("level/{levelNumber}") { LevelDetailScreen(...) }
}
```

## Build & Run

```bash
# Debug build
./gradlew assembleDebug

# APK location
app/build/outputs/apk/debug/app-debug.apk
```

To install on a connected device:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

Or open in Android Studio and click **Run**.

## License

This project is for educational purposes.
