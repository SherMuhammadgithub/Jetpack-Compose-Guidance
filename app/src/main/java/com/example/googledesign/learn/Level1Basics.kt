package com.example.googledesign.learn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ╔════════════════════════════════════════════════════════════╗
// ║            LEVEL 1: THE ABSOLUTE BASICS                   ║
// ║                                                           ║
// ║  Topic 1: What is @Composable?                           ║
// ║  Topic 2: Text, Button, Image                            ║
// ║  Topic 3: Modifier                                        ║
// ║  Topic 4: Column, Row, Box                                ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// TOPIC 1: What is @Composable?
// ═══════════════════════════════════════════════════════════
//
// In the OLD XML way, you would:
//   1. Create an XML file (activity_main.xml)
//   2. Put <TextView>, <Button> etc. in the XML
//   3. In Kotlin, call findViewById() to access them
//
// In Compose, you SKIP XML entirely. You write UI as Kotlin functions!
//
// RULE: Any function that draws UI must have @Composable annotation.
//
// Think of @Composable like a label that says:
//   "Hey Compose, this function creates something visible on screen"
//
// KEY RULES:
//   1. @Composable functions can ONLY be called from other @Composable functions
//   2. They don't RETURN a view — they EMIT (draw) UI directly
//   3. Function names should start with UPPERCASE (like a class name)
//      ✓ Greeting()    ✗ greeting()
//      ✓ MyButton()    ✗ myButton()

// Here's the simplest possible composable:
@Composable
fun SimpleGreeting() {
    // This function doesn't return anything.
    // It just says: "put this text on screen"
    Text("Hello, I am a Composable!")
}

// You can NEST composables (put one inside another):
@Composable
fun GreetingWithButton() {
    Column {                          // Column is also a @Composable
        Text("Hello!")                // Text is also a @Composable
        Button(onClick = { }) {       // Button is also a @Composable
            Text("Click me")          // Text INSIDE a Button
        }
    }
}
// See how they all stack? Composable → inside Composable → inside Composable
// This nesting is how you build complex UIs from simple pieces.


// ═══════════════════════════════════════════════════════════
// TOPIC 2: Text, Button, Image — The 3 Basic Building Blocks
// ═══════════════════════════════════════════════════════════

// --- TEXT ---
// Text() is the most basic composable. It shows text on screen.
// It has MANY parameters. Here are the most common:
@Composable
fun TextExamples() {
    Column {
        // Simplest text — just a string
        Text("Simple text")

        // Text with custom size
        Text(
            text = "Bigger text",
            fontSize = 24.sp          // sp = Scale-independent Pixels (for text)
        )

        // Text with color
        Text(
            text = "Colored text",
            color = Color.Blue
        )

        // Text with bold weight
        Text(
            text = "Bold text",
            fontWeight = FontWeight.Bold
        )

        // Text using THEME styles (the RIGHT way!)
        // Instead of hardcoding fontSize, use the theme:
        Text(
            text = "Theme styled text",
            style = MaterialTheme.typography.headlineSmall,  // From Type.kt!
            color = MaterialTheme.colorScheme.primary         // From Color.kt!
        )
        // Why is this better? Because if you change Type.kt,
        // ALL text using this style updates automatically!
    }
}

// --- BUTTON ---
// Button() is a clickable rectangle with Material 3 styling.
// It REQUIRES two things:
//   1. onClick = { } → what happens when you tap it
//   2. content = { } → what's INSIDE the button (usually Text)
@Composable
fun ButtonExamples() {
    Column {
        // Basic button
        Button(onClick = { /* do something when clicked */ }) {
            Text("Basic Button")
        }

        // Button with an icon + text
        Button(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Favorite,  // Built-in heart icon
                contentDescription = "Favorite"        // For accessibility (screen readers)
            )
            Spacer(modifier = Modifier.width(8.dp))    // 8dp gap between icon and text
            Text("Like")
        }

        // Note: contentDescription is for BLIND users.
        // Screen readers (TalkBack) read this aloud.
        // Use null ONLY for decorative icons.
    }
}

// --- IMAGE / ICON ---
// There are two ways to show images:
//   1. Icon() → for small, single-color icons
//   2. Image() → for photos, illustrations
@Composable
fun IconExamples() {
    Column {
        // Built-in Material icon
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = Color(0xFFFFD700)     // Gold color tint
        )

        // Larger icon with custom size
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Heart",
            modifier = Modifier.size(48.dp),   // Make it 48x48 dp
            tint = Color.Red
        )

        // To show an actual image from your drawable folder:
        // Image(
        //     painter = painterResource(id = R.drawable.my_image),
        //     contentDescription = "My photo"
        // )
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 3: Modifier — The Magic Chain
// ═══════════════════════════════════════════════════════════
//
// Modifier is HOW you change a composable's appearance & behavior.
// Think of it as a chain of instructions:
//
//   Modifier
//     .fillMaxWidth()     → "take full width"
//     .padding(16.dp)     → "add 16dp space inside"
//     .background(Blue)   → "paint background blue"
//
// IMPORTANT: ORDER MATTERS!
//
//   .padding(16.dp).background(Blue)
//   → First adds padding, THEN paints blue
//   → Result: blue area is SMALLER (padding is outside blue)
//
//   .background(Blue).padding(16.dp)
//   → First paints blue, THEN adds padding
//   → Result: blue area is BIGGER (padding is inside blue)
//
// Visual:
//   padding THEN background:       background THEN padding:
//   ┌──────────────────┐           ┌──────────────────┐
//   │  (empty space)   │           │▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓│
//   │  ┌────────────┐  │           │▓                ▓│
//   │  │▓▓▓▓BLUE▓▓▓▓│  │           │▓    CONTENT     ▓│
//   │  │▓ CONTENT  ▓│  │           │▓                ▓│
//   │  │▓▓▓▓▓▓▓▓▓▓▓▓│  │           │▓▓▓▓▓▓BLUE▓▓▓▓▓▓│
//   │  └────────────┘  │           └──────────────────┘
//   └──────────────────┘

@Composable
fun ModifierExamples() {
    Column(modifier = Modifier.padding(16.dp)) {

        // SIZE modifiers
        Text(
            "Full width text",
            modifier = Modifier.fillMaxWidth()     // Take 100% screen width
        )

        Text(
            "Fixed size box",
            modifier = Modifier
                .size(width = 200.dp, height = 40.dp)  // Exact size
                .background(Color.LightGray)
        )

        // PADDING modifier
        // padding = space INSIDE the composable's border
        Text(
            "Text with padding",
            modifier = Modifier
                .background(Color(0xFFE8EAED))
                .padding(16.dp)              // 16dp space on all sides
        )

        // PADDING with specific sides
        Text(
            "Specific padding",
            modifier = Modifier
                .background(Color(0xFFD3E3FD))
                .padding(
                    start = 24.dp,    // Left side (or right in RTL languages)
                    top = 8.dp,
                    end = 24.dp,      // Right side (or left in RTL)
                    bottom = 8.dp
                )
        )

        // CHAINING multiple modifiers (order matters!)
        Text(
            "Chained modifiers",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 4: Column, Row, Box — The 3 Layout Containers
// ═══════════════════════════════════════════════════════════
//
// Every UI is built with just these 3 layouts:
//
// COLUMN → stacks children VERTICALLY (top to bottom)
//   ┌─────┐
//   │  A  │
//   │  B  │
//   │  C  │
//   └─────┘
//
// ROW → places children HORIZONTALLY (left to right)
//   ┌───────────┐
//   │  A  B  C  │
//   └───────────┘
//
// BOX → stacks children ON TOP of each other (like layers)
//   ┌─────┐
//   │  C  │  ← C is on top
//   │  B  │  ← B is behind C
//   │  A  │  ← A is at the bottom
//   └─────┘

@Composable
fun ColumnExample() {
    // Column = VERTICAL stack
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F3F4))
            .padding(16.dp),

        // verticalArrangement = how items are spaced VERTICALLY
        //   Top         → all items at top
        //   Bottom      → all items at bottom
        //   Center      → all items in middle
        //   SpaceBetween → first at top, last at bottom, rest evenly spaced
        //   SpaceEvenly  → all items evenly spaced
        //   SpaceAround  → like SpaceEvenly but with half-space at edges
        verticalArrangement = Arrangement.spacedBy(8.dp),  // 8dp gap between each item

        // horizontalAlignment = how items align HORIZONTALLY
        //   Start           → left side
        //   End             → right side
        //   CenterHorizontally → center
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Item 1", style = MaterialTheme.typography.bodyLarge)
        Text("Item 2", style = MaterialTheme.typography.bodyLarge)
        Text("Item 3", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun RowExample() {
    // Row = HORIZONTAL layout
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD3E3FD))
            .padding(16.dp),

        // horizontalArrangement = how items are spaced HORIZONTALLY
        horizontalArrangement = Arrangement.SpaceBetween,

        // verticalAlignment = how items align VERTICALLY
        //   Top          → top edge
        //   Bottom       → bottom edge
        //   CenterVertically → middle
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Left")
        Text("Center")
        Text("Right")
    }
}

@Composable
fun BoxExample() {
    // Box = LAYERED layout (things stack on top of each other)
    // Perfect for: overlapping elements, badges, profile picture with status dot
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color(0xFFE8EAED))
    ) {
        // First child = bottom layer
        Text(
            "Bottom",
            modifier = Modifier.align(Alignment.Center),  // In a Box, each child picks its own position
            color = Color.Gray
        )
        // Second child = on top of first
        Text(
            "Top Right",
            modifier = Modifier.align(Alignment.TopEnd),   // Position: top-right corner
            fontSize = 10.sp,
            color = Color.Red
        )
    }
}

// ═══════════════════════════════════════════════════════════
// COMBINING EVERYTHING — A Real-World Example
// ═══════════════════════════════════════════════════════════
//
// Let's build a simple "User Card" using everything we learned:
//   - @Composable function
//   - Text, Icon, Button
//   - Modifier for styling
//   - Column + Row + Box for layout

@Composable
fun UserCard() {
    // Outer Row: icon on left, text on right
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile icon (using a Box as a circle placeholder)
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = androidx.compose.foundation.shape.CircleShape  // Makes it round!
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "A",   // First letter of name
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Spacer = empty space (like margin between elements)
        Spacer(modifier = Modifier.width(12.dp))

        // Column for name + email (stacked vertically)
        Column(modifier = Modifier.weight(1f)) {
            // weight(1f) means: "take all remaining space"
            // Without weight, the Column might push the button off screen
            Text(
                "Alex Student",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "alex@example.com",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Follow button on the right
        Button(onClick = { }) {
            Text("Follow")
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN — Shows all examples together
// ═══════════════════════════════════════════════════════════

@Composable
fun Level1Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // Makes it scrollable!
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // --- Section: Topic 1 - @Composable ---
        Text(
            "Topic 1: @Composable",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        SimpleGreeting()
        GreetingWithButton()

        // --- Divider line ---
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )

        // --- Section: Topic 2 - Text, Button, Icon ---
        Text(
            "Topic 2: Text",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        TextExamples()

        Text(
            "Topic 2: Button",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        ButtonExamples()

        Text(
            "Topic 2: Icon",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        IconExamples()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )

        // --- Section: Topic 3 - Modifier ---
        Text(
            "Topic 3: Modifier",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        ModifierExamples()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )

        // --- Section: Topic 4 - Column, Row, Box ---
        Text(
            "Topic 4: Column",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        ColumnExample()

        Text(
            "Topic 4: Row",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        RowExample()

        Text(
            "Topic 4: Box",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        BoxExample()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )

        // --- Combined Example ---
        Text(
            "Combined: User Card",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        UserCard()

        // Bottom spacing
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ═══════════════════════════════════════════════════════════
// PREVIEWS — See these in Android Studio without running app
// ═══════════════════════════════════════════════════════════

@Preview(showBackground = true, name = "Level 1 - Full Screen")
@Composable
fun Level1ScreenPreview() {
    GoogleDesignTheme {
        Level1Screen()
    }
}

@Preview(showBackground = true, name = "User Card Only")
@Composable
fun UserCardPreview() {
    GoogleDesignTheme {
        UserCard()
    }
}
