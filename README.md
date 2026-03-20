# Jetpack Compose Guidance

A minimal, practical guide to building Android apps with **Jetpack Compose** in Kotlin.

---

## Table of Contents

1. [What is Jetpack Compose?](#1-what-is-jetpack-compose)
2. [Prerequisites](#2-prerequisites)
3. [Create a New Project](#3-create-a-new-project)
4. [Gradle Setup](#4-gradle-setup)
5. [Your First Composable](#5-your-first-composable)
6. [Modifiers](#6-modifiers)
7. [Layouts](#7-layouts)
8. [Common UI Components](#8-common-ui-components)
9. [State Management](#9-state-management)
10. [Navigation](#10-navigation)
11. [Complete Minimal App Example](#11-complete-minimal-app-example)
12. [Next Steps](#12-next-steps)

---

## 1. What is Jetpack Compose?

Jetpack Compose is Android's modern **declarative UI toolkit**. Instead of building UIs with XML layouts, you describe what the UI should look like using Kotlin functions annotated with `@Composable`. The framework automatically updates the UI when the underlying data changes.

Key advantages:
- **Less code** — no XML, no `findViewById`, no view binding boilerplate
- **Declarative** — describe the UI state, Compose handles updates
- **Interoperable** — works alongside existing Views and the Android ecosystem
- **Kotlin-first** — built for and with Kotlin

---

## 2. Prerequisites

| Requirement | Minimum version |
|---|---|
| Android Studio | Hedgehog (2023.1.1) or newer |
| Kotlin | 1.9.x |
| Compose BOM | 2024.x |
| Min SDK | 21 (Android 5.0) |
| Compile SDK | 34 or higher |

Install **Android Studio** from [https://developer.android.com/studio](https://developer.android.com/studio).

---

## 3. Create a New Project

1. Open Android Studio → **File → New → New Project**
2. Select the **"Empty Activity"** template (uses Compose by default)
3. Set your **Package name**, **Save location**, and choose **Kotlin** as the language
4. Set **Minimum SDK** to API 21 or higher
5. Click **Finish**

Android Studio generates a project already wired for Compose, including a sample `MainActivity` and a `@Composable` greeting function.

---

## 4. Gradle Setup

Android Studio handles most of the setup automatically. Here is what the essential parts look like.

### `build.gradle.kts` (app module)

```kotlin
android {
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }

    buildFeatures {
        compose = true          // enable Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.0")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

> **Tip:** Using the **Compose BOM** (Bill of Materials) keeps all Compose library versions in sync automatically — you don't need to specify individual version numbers.

---

## 5. Your First Composable

A **Composable function** is a regular Kotlin function annotated with `@Composable`. It describes a piece of the UI.

```kotlin
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}
```

### Attaching Compose to an Activity

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    Greeting(name = "World")
                }
            }
        }
    }
}
```

`setContent { }` replaces the traditional `setContentView(R.layout.*)` call and is the entry point into the Compose world.

### Previewing Composables

Add `@Preview` to see your composable directly in Android Studio without running the app:

```kotlin
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Greeting(name = "Preview")
    }
}
```

---

## 6. Modifiers

**Modifiers** let you decorate or configure a composable — size, padding, background, click handling, and more. They are chained with `.` and passed as the `modifier` parameter.

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StyledText() {
    Text(
        text = "Styled!",
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
    )
}
```

Common modifiers:

| Modifier | Purpose |
|---|---|
| `padding(dp)` | Inner space around the composable |
| `fillMaxWidth()` / `fillMaxSize()` | Expand to available width/size |
| `size(dp)` | Fixed width and height |
| `background(color)` | Background color or shape |
| `clickable { }` | Handle tap events |
| `clip(shape)` | Clip to a shape (e.g. `RoundedCornerShape`) |

---

## 7. Layouts

Compose provides three fundamental layout containers.

### `Column` — vertical arrangement

```kotlin
import androidx.compose.foundation.layout.Column

@Composable
fun VerticalList() {
    Column {
        Text("First")
        Text("Second")
        Text("Third")
    }
}
```

### `Row` — horizontal arrangement

```kotlin
import androidx.compose.foundation.layout.Row

@Composable
fun HorizontalItems() {
    Row {
        Text("Left")
        Text("Right")
    }
}
```

### `Box` — stacking / overlapping

```kotlin
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment

@Composable
fun CenteredContent() {
    Box(contentAlignment = Alignment.Center) {
        Text("Centered")
    }
}
```

### Alignment and arrangement

```kotlin
Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text("Item 1")
    Text("Item 2")
}
```

---

## 8. Common UI Components

### Text

```kotlin
Text(
    text = "Hello Compose",
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.primary
)
```

### Button

```kotlin
import androidx.compose.material3.Button

Button(onClick = { /* handle click */ }) {
    Text("Click Me")
}
```

### TextField (text input)

```kotlin
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*

@Composable
fun NameInput() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Name") }
    )
}
```

### Image

```kotlin
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

Image(
    painter = painterResource(id = R.drawable.ic_launcher_foreground),
    contentDescription = "App icon"
)
```

### LazyColumn (scrollable list)

```kotlin
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun ItemList(items: List<String>) {
    LazyColumn {
        items(items) { item ->
            Text(
                text = item,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

---

## 9. State Management

Compose is **reactive**: when state changes, the UI recomposes automatically.

### `remember` + `mutableStateOf`

```kotlin
import androidx.compose.runtime.*

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}
```

- `mutableStateOf(value)` — creates an observable state holder
- `remember { }` — keeps the value alive across recompositions

### State hoisting

Move state **up** to a parent composable so child composables stay stateless and reusable:

```kotlin
// Stateless child — accepts value and callback
@Composable
fun CounterDisplay(count: Int, onIncrement: () -> Unit) {
    Column {
        Text("Count: $count")
        Button(onClick = onIncrement) { Text("Increment") }
    }
}

// Stateful parent — owns the state
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    CounterDisplay(count = count, onIncrement = { count++ })
}
```

### ViewModel (recommended for production)

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun increment() { _count.value++ }
}

@Composable
fun CounterScreen(vm: CounterViewModel = viewModel()) {
    val count by vm.count.collectAsState()
    Column {
        Text("Count: $count")
        Button(onClick = vm::increment) { Text("Increment") }
    }
}
```

Add the dependency to use `viewModel()`:

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
```

---

## 10. Navigation

Navigation between screens uses the **Navigation Compose** library.

### Dependency

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```

### Basic setup

```kotlin
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(onNavigate = { navController.navigate("detail") })
        }

        composable("detail") {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
```

### Passing arguments

```kotlin
composable("detail/{itemId}") { backStackEntry ->
    val itemId = backStackEntry.arguments?.getString("itemId")
    DetailScreen(itemId = itemId)
}

// Navigate with argument
navController.navigate("detail/42")
```

---

## 11. Complete Minimal App Example

This self-contained example brings together all the concepts above: state, layouts, components, and navigation.

### Project structure

```
app/
└── src/main/java/com/example/myapp/
    ├── MainActivity.kt
    ├── ui/
    │   ├── HomeScreen.kt
    │   └── DetailScreen.kt
    └── navigation/
        └── AppNavigation.kt
```

### `MainActivity.kt`

```kotlin
package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.myapp.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}
```

### `navigation/AppNavigation.kt`

```kotlin
package com.example.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.DetailScreen
import com.example.myapp.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onOpenDetail = { name ->
                navController.navigate("detail/$name")
            })
        }
        composable("detail/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            DetailScreen(name = name, onBack = { navController.popBackStack() })
        }
    }
}
```

### `ui/HomeScreen.kt`

```kotlin
package com.example.myapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onOpenDetail: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Compose!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { if (name.isNotBlank()) onOpenDetail(name) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Detail")
        }
    }
}
```

### `ui/DetailScreen.kt`

```kotlin
package com.example.myapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(name: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello, $name!",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}
```

---

## 12. Next Steps

Once you have the basics working, explore these topics to build production-quality apps:

| Topic | Resource |
|---|---|
| **Theming** | [Material 3 Theming](https://developer.android.com/jetpack/compose/designsystems/material3) |
| **Lists & Grids** | `LazyColumn`, `LazyRow`, `LazyVerticalGrid` |
| **Side effects** | `LaunchedEffect`, `DisposableEffect`, `SideEffect` |
| **Animations** | `AnimatedVisibility`, `animateContentSize`, `Transition` |
| **Testing** | `ComposeTestRule`, `onNodeWithText`, `performClick` |
| **Dependency Injection** | [Hilt with Compose](https://developer.android.com/training/dependency-injection/hilt-android) |
| **Async / Coroutines** | `rememberCoroutineScope`, `Flow` + `collectAsState()` |
| **Official samples** | [github.com/android/compose-samples](https://github.com/android/compose-samples) |

---

> **Official documentation:** [developer.android.com/jetpack/compose](https://developer.android.com/jetpack/compose)