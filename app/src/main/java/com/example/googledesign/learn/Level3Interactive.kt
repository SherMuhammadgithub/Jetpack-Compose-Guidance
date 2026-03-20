package com.example.googledesign.learn

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ╔════════════════════════════════════════════════════════════╗
// ║          LEVEL 3: MAKING THINGS INTERACTIVE               ║
// ║                                                           ║
// ║  Topic 8:  State (remember & mutableStateOf)             ║
// ║  Topic 9:  TextField (user input)                        ║
// ║  Topic 10: Clicks, Toggles, Switches                    ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// TOPIC 8: State — THE Most Important Concept in Compose
// ═══════════════════════════════════════════════════════════
//
// THE BIG QUESTION:
//   In XML, you'd do: textView.text = "new text"
//   But in Compose, there's no textView object. Functions just run
//   and draw pixels. So how do you UPDATE the screen?
//
// ANSWER: STATE + RECOMPOSITION
//
// How it works:
//   1. You create a "state" variable using remember + mutableStateOf
//   2. You use that variable in your UI
//   3. When the state CHANGES, Compose automatically REDRAWS
//      (only the parts that use that state — very efficient!)
//
// Think of it like a spreadsheet:
//   Cell A1 = 5          ← this is STATE
//   Cell B1 = A1 * 2     ← this DEPENDS on state
//   When you change A1 to 10, B1 automatically becomes 20
//   That's exactly how Compose works!
//
// SYNTAX:
//   var count by remember { mutableStateOf(0) }
//   │   │      │            │
//   │   │      │            └── Starting value = 0
//   │   │      └── remember = "don't reset when screen redraws"
//   │   └── The variable name
//   └── var (not val!) because it will change
//
// WITHOUT remember:
//   Every time Compose redraws, the variable resets to 0!
//   remember tells Compose: "keep this value between redraws"
//
// WITHOUT mutableStateOf:
//   Compose won't KNOW the value changed, so it won't redraw!
//   mutableStateOf = "hey Compose, watch this variable for changes"
// ═══════════════════════════════════════════════════════════


// --- 8A: The Simplest Counter ---

@Composable
fun SimpleCounter() {
    // STEP 1: Create state
    // "remember" = survive redraws
    // "mutableStateOf(0)" = start at 0, Compose watches for changes
    // "by" = delegate (lets you use count directly instead of count.value)
    var count by remember { mutableIntStateOf(0) }

    // STEP 2: Use state in UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Count: $count",                // Uses state!
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // STEP 3: Change state on click
            // When count changes → Compose redraws → Text shows new value
            Button(onClick = { count-- }) {        // Decrease
                Text("-")
            }

            Button(onClick = { count = 0 }) {     // Reset
                Text("Reset")
            }

            Button(onClick = { count++ }) {        // Increase
                Text("+")
            }
        }

        // This text ALSO uses count, so it ALSO updates automatically!
        Text(
            text = when {
                count > 10 -> "That's a lot! 🎉"
                count > 0 -> "Keep going!"
                count == 0 -> "Start counting!"
                else -> "Going negative!"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


// --- 8B: State with Text (Dynamic UI) ---

@Composable
fun DynamicGreeting() {
    // State: the user's name
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        // The greeting changes based on name state
        Text(
            text = if (name.isEmpty()) "Type your name below!"
                   else "Hello, $name! 👋",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TextField updates the name state as user types
        // We'll learn TextField in detail in Topic 9!
        OutlinedTextField(
            value = name,                           // Shows current state
            onValueChange = { name = it },          // Updates state on every keystroke
            label = { Text("Your name") },
            modifier = Modifier.fillMaxWidth()
        )

        // This shows letter count — also reacts to name changes!
        Text(
            text = "Characters: ${name.length}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


// --- 8C: State with Boolean (Toggle UI) ---

@Composable
fun ToggleExample() {
    // Boolean state: expanded or collapsed
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Click to expand",
                style = MaterialTheme.typography.titleMedium
            )

            // Button toggles the boolean
            Button(onClick = { isExpanded = !isExpanded }) {
                // Button text changes based on state!
                Text(if (isExpanded) "Collapse" else "Expand")
            }
        }

        // This section ONLY shows when isExpanded is true
        // This is how you conditionally show/hide UI in Compose!
        if (isExpanded) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "This is the hidden content that appears when " +
                "you click Expand! In Compose, you just use " +
                "if/else to show or hide composables. No need for " +
                "View.VISIBLE or View.GONE like in XML!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// --- 8D: Multiple States Together ---

@Composable
fun ColorPickerDemo() {
    // Multiple states working together
    var selectedColor by remember { mutableStateOf("Blue") }
    var size by remember { mutableIntStateOf(60) }

    val actualColor = when (selectedColor) {
        "Blue" -> Color(0xFF1A73E8)
        "Red" -> Color(0xFFD93025)
        "Green" -> Color(0xFF188038)
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Preview box — reacts to BOTH color and size state
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(actualColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Color buttons
        Text("Pick a color:", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Blue", "Red", "Green").forEach { color ->
                Button(
                    onClick = { selectedColor = color },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedColor == color)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = if (selectedColor == color)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(color)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Size buttons
        Text("Size: ${size}dp", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { if (size > 20) size -= 20 }) { Text("Smaller") }
            Button(onClick = { if (size < 160) size += 20 }) { Text("Bigger") }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 9: TextField — User Input
// ═══════════════════════════════════════════════════════════
//
// TextField = the Compose version of EditText (text input field)
//
// Two main types:
//   TextField()         → filled style (Material 3, has background color)
//   OutlinedTextField() → outlined style (just a border, no fill)
//
// IMPORTANT: TextField in Compose is "controlled"
//   - YOU store the text in a state variable
//   - TextField SHOWS that state
//   - When user types, onValueChange fires, YOU update the state
//   - TextField re-renders with new state
//
//   var text by remember { mutableStateOf("") }
//   TextField(
//       value = text,                    ← shows current state
//       onValueChange = { text = it }    ← updates state on keystroke
//   )
//
// In XML, EditText manages its own text internally.
// In Compose, YOU own the text. This gives you full control
// (validation, formatting, etc.)
// ═══════════════════════════════════════════════════════════


// --- 9A: Filled vs Outlined ---

@Composable
fun TextFieldTypes() {
    var filledText by remember { mutableStateOf("") }
    var outlinedText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // FILLED TextField — has a background color
        // Best for: primary input fields, forms
        TextField(
            value = filledText,
            onValueChange = { filledText = it },
            label = { Text("Filled TextField") },   // Floating label
            modifier = Modifier.fillMaxWidth()
        )

        // OUTLINED TextField — just a border
        // Best for: secondary inputs, cleaner look
        OutlinedTextField(
            value = outlinedText,
            onValueChange = { outlinedText = it },
            label = { Text("Outlined TextField") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


// --- 9B: TextField with Icons & Placeholder ---

@Composable
fun TextFieldWithExtras() {
    var searchText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // Search field with leading icon, placeholder, and clear button
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search anything...") },  // Shows when empty

            // leadingIcon = icon at the START of the field
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            },

            // trailingIcon = icon at the END of the field
            // Here: show clear button only when there's text
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {  // Clear text!
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            },

            // singleLine = prevent multi-line input
            singleLine = true,

            shape = RoundedCornerShape(12.dp)
        )

        // Show what user typed
        if (searchText.isNotEmpty()) {
            Text(
                "Searching for: \"$searchText\"",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


// --- 9C: Password Field ---

@Composable
fun PasswordField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        leadingIcon = {
            Icon(Icons.Filled.Lock, contentDescription = "Password")
        },

        // trailingIcon: toggle password visibility
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility
                                  else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password"
                                         else "Show password"
                )
            }
        },

        // visualTransformation: hide or show the actual characters
        // PasswordVisualTransformation() → shows dots (●●●●●)
        // VisualTransformation.None → shows actual text
        visualTransformation = if (passwordVisible) VisualTransformation.None
                               else PasswordVisualTransformation(),

        // keyboardOptions: controls the keyboard type and actions
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,    // Suggests password keyboard
            imeAction = ImeAction.Done               // "Done" button on keyboard
        ),

        singleLine = true
    )
}


// --- 9D: Different Keyboard Types ---

@Composable
fun KeyboardTypeExamples() {
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // EMAIL keyboard — shows @ and .com buttons
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,    // Email keyboard layout
                imeAction = ImeAction.Next            // "Next" button → moves to next field
            ),
            singleLine = true
        )

        // PHONE keyboard — only numbers + phone symbols
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Phone") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,    // Phone number keyboard
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        // NUMBER keyboard — only numbers
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,   // Number-only keyboard
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        // SUMMARY of keyboard types:
        // KeyboardType.Text     → default keyboard
        // KeyboardType.Email    → has @ and .com
        // KeyboardType.Phone    → phone dialer
        // KeyboardType.Number   → numbers only
        // KeyboardType.Password → may hide suggestions
        // KeyboardType.Uri      → has / and .com
        //
        // ImeAction types (the bottom-right keyboard button):
        // ImeAction.Done    → "Done" (closes keyboard)
        // ImeAction.Next    → "Next" (moves to next field)
        // ImeAction.Search  → "Search" icon
        // ImeAction.Send    → "Send" icon
        // ImeAction.Go      → "Go" icon
    }
}


// --- 9E: Validation Example ---

@Composable
fun ValidationExample() {
    var email by remember { mutableStateOf("") }

    // Compute validation state from the email
    val isError = email.isNotEmpty() && !email.contains("@")

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },

            // isError = shows red border when true
            isError = isError,

            // supportingText = helper text below the field
            supportingText = {
                if (isError) {
                    Text(
                        "Email must contain @",
                        color = MaterialTheme.colorScheme.error
                    )
                } else if (email.isNotEmpty()) {
                    Text(
                        "Looks good!",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 10: Clicks, Toggles, Switches
// ═══════════════════════════════════════════════════════════
//
// Interactive components that respond to user actions.
// All of them need STATE to work!
// ═══════════════════════════════════════════════════════════


// --- 10A: All Button Types ---
//
// Material 3 has 5 button types, each for different importance:
//
//   Button()           → filled, highest emphasis    "Save"
//   FilledTonalButton  → softer fill, medium emphasis "Open"
//   ElevatedButton     → shadow, medium emphasis     "Details"
//   OutlinedButton     → just a border, low emphasis "Cancel"
//   TextButton         → just text, lowest emphasis  "Skip"
//
//   Visual:
//   [████SAVE████]  [░░░Open░░░]  [Details]  |Cancel|  Skip

@Composable
fun AllButtonTypes() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Button(onClick = { }) {
            Text("Filled (highest emphasis)")
        }

        FilledTonalButton(onClick = { }) {
            Text("Filled Tonal (medium)")
        }

        ElevatedButton(onClick = { }) {
            Text("Elevated (medium)")
        }

        OutlinedButton(onClick = { }) {
            Text("Outlined (low emphasis)")
        }

        TextButton(onClick = { }) {
            Text("Text Button (lowest)")
        }
    }
}


// --- 10B: Clickable Modifier ---
//
// .clickable { } makes ANY composable tappable
// Unlike Button(), it has no built-in styling — you control everything

@Composable
fun ClickableExample() {
    var tapCount by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // A Card that's clickable
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { tapCount++ },     // Make the whole card tappable!
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Tap this card!",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Tapped: $tapCount",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // A custom "chip" made clickable
        // This shows you can make ANYTHING clickable — not just buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val options = listOf("Design", "Code", "Test")
            var selected by remember { mutableStateOf("Design") }

            options.forEach { option ->
                Text(
                    text = option,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (selected == option) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable { selected = option }     // Tapping changes selection
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (selected == option) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// --- 10C: Switch & Checkbox ---

@Composable
fun SwitchAndCheckbox() {
    var notificationsOn by remember { mutableStateOf(true) }
    var darkModeOn by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        Text("Switches:", style = MaterialTheme.typography.titleSmall)

        // SWITCH — on/off toggle (like iOS switch)
        // Best for: settings that take effect immediately
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { notificationsOn = !notificationsOn }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Notifications")
            Switch(
                checked = notificationsOn,                     // State!
                onCheckedChange = { notificationsOn = it }     // Update state!
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { darkModeOn = !darkModeOn }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = darkModeOn,
                onCheckedChange = { darkModeOn = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Checkboxes:", style = MaterialTheme.typography.titleSmall)

        // CHECKBOX — checked/unchecked
        // Best for: multiple selections, forms, agreements
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { agreeTerms = !agreeTerms }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreeTerms,
                onCheckedChange = { agreeTerms = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("I agree to the terms")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { rememberMe = !rememberMe }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Remember me")
        }
    }
}


// --- 10D: Radio Buttons ---
//
// RadioButton = select ONE from a group (unlike Checkbox which is multi-select)
// Best for: gender, payment method, sort order

@Composable
fun RadioButtonExample() {
    val options = listOf("Student", "Teacher", "Developer")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column {
        Text("I am a:", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(4.dp))

        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = option }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),       // Only ONE selected
                    onClick = { selectedOption = option }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(option)
            }
        }

        Text(
            "Selected: $selectedOption",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


// --- 10E: Slider ---
//
// Slider = drag to pick a value in a range
// Best for: volume, brightness, price range

@Composable
fun SliderExample() {
    var sliderValue by remember { mutableFloatStateOf(50f) }

    Column {
        Text(
            "Volume: ${sliderValue.toInt()}%",
            style = MaterialTheme.typography.titleSmall
        )

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f,     // Min to Max
            steps = 9,                  // Snap points (divides range into 10 segments)
            modifier = Modifier.fillMaxWidth()
        )

        // Visual volume bar using the slider value
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = sliderValue / 100f)   // Width = percentage!
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}


// --- 10F: Like Button (Practical Example) ---

@Composable
fun LikeButton() {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(42) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Great post about Compose!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Heart icon that toggles
        IconButton(onClick = {
            isLiked = !isLiked
            likeCount += if (isLiked) 1 else -1
        }) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite
                              else Icons.Filled.FavoriteBorder,
                contentDescription = if (isLiked) "Unlike" else "Like",
                tint = if (isLiked) Color.Red
                       else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "$likeCount",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


// ═══════════════════════════════════════════════════════════
// COMBINED: Mini Login Form (Uses EVERYTHING from Level 3)
// ═══════════════════════════════════════════════════════════

@Composable
fun MiniLoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }
//    val isError = email.isNotEmpty() && !email.contains("@")
    var isError = email.isNotEmpty() && !email.contains("@");
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Welcome Back",
                style = MaterialTheme.typography.headlineSmall
            )

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = isError
            )

            if(isError) {
                Text("Email is invlaid", color = MaterialTheme.colorScheme.error)
            }
            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                                       else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            // Remember me checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Remember me", style = MaterialTheme.typography.bodyMedium)
            }

            // Login button
            Button(
                onClick = { isSubmitted = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotEmpty() && password.isNotEmpty() && !isError  // Disabled when empty!
            ) {
                Text("Login")
            }

            // Result (shown after clicking Login)
            if (isSubmitted) {
                Text(
                    "Email: $email\nRemember: $rememberMe",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                )
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN
// ═══════════════════════════════════════════════════════════

@Composable
fun Level3Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── TOPIC 8: State ──
        SectionTitle("Topic 8A: Simple Counter")
        SimpleCounter()

        DividerLine()

        SectionTitle("Topic 8B: Dynamic Greeting")
        DynamicGreeting()

        DividerLine()

        SectionTitle("Topic 8C: Toggle (Show/Hide)")
        ToggleExample()

        DividerLine()

        SectionTitle("Topic 8D: Color Picker")
        ColorPickerDemo()

        DividerLine()

        // ── TOPIC 9: TextField ──
        SectionTitle("Topic 9A: TextField Types")
        TextFieldTypes()

        DividerLine()

        SectionTitle("Topic 9B: Search with Icons")
        TextFieldWithExtras()

        DividerLine()

        SectionTitle("Topic 9C: Password Field")
        PasswordField()

        DividerLine()

        SectionTitle("Topic 9D: Keyboard Types")
        KeyboardTypeExamples()

        DividerLine()

        SectionTitle("Topic 9E: Validation")
        ValidationExample()

        DividerLine()

        // ── TOPIC 10: Clicks, Toggles ──
        SectionTitle("Topic 10A: Button Types")
        AllButtonTypes()

        DividerLine()

        SectionTitle("Topic 10B: Clickable Anything")
        ClickableExample()

        DividerLine()

        SectionTitle("Topic 10C: Switch & Checkbox")
        SwitchAndCheckbox()

        DividerLine()

        SectionTitle("Topic 10D: Radio Buttons")
        RadioButtonExample()

        DividerLine()

        SectionTitle("Topic 10E: Slider")
        SliderExample()

        DividerLine()

        SectionTitle("Topic 10F: Like Button")
        LikeButton()

        DividerLine()

        // ── Combined ──
        SectionTitle("Combined: Mini Login Form")
        MiniLoginForm()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ── Helpers ──

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun DividerLine() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    )
}


// ═══════════════════════════════════════════════════════════
// PREVIEWS
// ═══════════════════════════════════════════════════════════

@Preview(showBackground = true, name = "Level 3 - Full Screen")
@Composable
fun Level3ScreenPreview() {
    GoogleDesignTheme {
        Level3Screen()
    }
}

@Preview(showBackground = true, name = "Login Form")
@Composable
fun LoginFormPreview() {
    GoogleDesignTheme {
        MiniLoginForm()
    }
}
