package com.example.googledesign.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ╔════════════════════════════════════════════════════════════╗
// ║          LEVEL 5: REAL APP PATTERNS                       ║
// ║                                                           ║
// ║  Topic 14: Scaffold, TopAppBar, BottomNav (full screens) ║
// ║  Topic 15: ViewModel + Compose (data → UI)               ║
// ║  Topic 16: Recomposition (when & why Compose redraws)    ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// TOPIC 14: Full Screen Patterns
// ═══════════════════════════════════════════════════════════
//
// In Level 4, we saw individual pieces (TopAppBar, NavigationBar,
// Scaffold). Now let's build COMPLETE SCREENS like real apps.
//
// A real app screen has:
//   1. A Scaffold (skeleton)
//   2. A TopAppBar (title + actions)
//   3. Content (your actual UI)
//   4. Sometimes: BottomNav, FAB, Snackbar
//
// We'll also learn STATE HOISTING — a key pattern where
// the parent owns the state and passes it DOWN to children.
// ═══════════════════════════════════════════════════════════


// --- 14A: Settings Screen Pattern ---
//
// A common pattern: list of settings with sections.
// Uses: Scaffold + TopAppBar + LazyColumn

data class SettingItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

val generalSettings = listOf(
    SettingItem(Icons.Filled.Person, "Account", "Manage your account details"),
    SettingItem(Icons.Filled.Favorite, "Appearance", "Theme, colors, fonts"),
    SettingItem(Icons.Filled.Search, "Privacy", "Control your data"),
)

val otherSettings = listOf(
    SettingItem(Icons.Filled.Home, "About", "App version and info"),
    SettingItem(Icons.Filled.Settings, "Advanced", "Developer options"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    Scaffold(
        modifier = Modifier.height(450.dp),
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Section header
            item {
                Text(
                    "General",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(generalSettings) { setting ->
                SettingRow(setting)
            }

            item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }

            item {
                Text(
                    "Other",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(otherSettings) { setting ->
                SettingRow(setting)
            }
        }
    }
}

@Composable
fun SettingRow(setting: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            setting.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(setting.title, style = MaterialTheme.typography.bodyLarge)
            Text(
                setting.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// --- 14B: Shopping Cart Screen Pattern ---
//
// Pattern: List of items + summary at bottom + FAB
// Shows: Scaffold + TopAppBar + LazyColumn + sticky bottom

data class CartItem(
    val name: String,
    val price: Int,
    val quantity: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen() {
    val cartItems = remember {
        mutableStateListOf(
            CartItem("Pixel Phone", 699, 1),
            CartItem("Pixel Buds", 199, 2),
            CartItem("Phone Case", 29, 1),
        )
    }

    val total = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        modifier = Modifier.height(450.dp),
        topBar = {
            TopAppBar(
                title = { Text("Cart (${cartItems.size})") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        // Bottom bar for total + checkout
        bottomBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total", style = MaterialTheme.typography.bodySmall)
                        Text(
                            "$$total",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(onClick = { }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Checkout")
                    }
                }
            }
        }
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            // Empty state — shown when cart is empty
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Cart is empty",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onDelete = { cartItems.remove(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartItem, onDelete: () -> Unit) {
    // STATE HOISTING in action!
    // This composable does NOT own the delete logic.
    // It receives onDelete from its parent.
    // The parent decides WHAT happens when delete is clicked.
    // This makes CartItemCard reusable!

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product icon placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    item.name.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleSmall)
                Text(
                    "$${item.price} × ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                "$${item.price * item.quantity}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = onDelete) {    // Calls parent's logic!
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


// --- 14C: Search Screen Pattern ---
//
// Pattern: Search bar at top + filtered results below
// Shows: State hoisting, filtering lists, empty states

data class AppItem(
    val name: String,
    val category: String,
    val rating: Float
)

val allApps = listOf(
    AppItem("Instagram", "Social", 4.5f),
    AppItem("WhatsApp", "Communication", 4.3f),
    AppItem("YouTube", "Entertainment", 4.6f),
    AppItem("Spotify", "Music", 4.4f),
    AppItem("Netflix", "Entertainment", 4.5f),
    AppItem("Twitter", "Social", 4.0f),
    AppItem("Telegram", "Communication", 4.5f),
    AppItem("Maps", "Navigation", 4.3f),
    AppItem("Chrome", "Productivity", 4.2f),
    AppItem("Gmail", "Productivity", 4.4f),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    // Filter list based on search — this recalculates every time searchQuery changes!
    val filteredApps = if (searchQuery.isEmpty()) {
        allApps
    } else {
        allApps.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        modifier = Modifier.height(450.dp),
        topBar = {
            TopAppBar(
                title = { Text("App Store") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search apps or categories...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Result count
            Text(
                "${filteredApps.size} results",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            if (filteredApps.isEmpty()) {
                // EMPTY STATE — no results found
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "No apps found for \"$searchQuery\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredApps) { app ->
                        AppRow(app = app)
                    }
                }
            }
        }
    }
}

@Composable
fun AppRow(app: AppItem) {
    var isInstalled by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                app.name.first().toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(app.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                "${app.category} • ★ ${app.rating}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isInstalled) {
            OutlinedButton(onClick = { isInstalled = false }) {
                Text("Open")
            }
        } else {
            Button(onClick = { isInstalled = true }) {
                Text("Install")
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 15: ViewModel + Compose (State Management)
// ═══════════════════════════════════════════════════════════
//
// THE PROBLEM:
//   In Level 3, we used "remember" to store state.
//   But "remember" has a BIG problem:
//     - Rotate your phone → state is LOST!
//     - Navigate away and come back → state is LOST!
//
//   Why? Because "remember" only survives RECOMPOSITION.
//   It dies when the Activity is destroyed (rotation, back press).
//
// THE SOLUTION: ViewModel
//   ViewModel survives:
//     ✓ Recomposition (like remember)
//     ✓ Screen rotation
//     ✓ Configuration changes
//     ✗ App killed by system (use SavedStateHandle for that)
//
// HOW IT WORKS:
//
//   ┌─────────────────────────────────────┐
//   │           ViewModel                  │
//   │  (lives outside the composable)      │
//   │                                      │
//   │  state: list of items               │
//   │  functions: addItem(), deleteItem()  │
//   │                                      │
//   │  Survives rotation!                  │
//   └──────────────┬──────────────────────┘
//                   │ state flows DOWN
//                   │ events flow UP
//   ┌──────────────▼──────────────────────┐
//   │           Screen                     │
//   │  (composable function)               │
//   │                                      │
//   │  reads state → shows UI              │
//   │  user clicks → calls ViewModel func  │
//   └──────────────────────────────────────┘
//
// WE WON'T ADD THE VIEWMODEL LIBRARY NOW (to keep it simple).
// Instead, I'll show you the PATTERN using a regular class.
// The concept is identical — just swap the class later.
//
// To use real ViewModel, you'd add:
//   implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.x")
// And extend ViewModel() class.
// ═══════════════════════════════════════════════════════════


// --- 15A: The Problem — State Lost on Reconfig ---

@Composable
fun RememberProblemDemo() {
    // This counter RESETS to 0 if you rotate the phone!
    // Because "remember" only survives recomposition, not recreation.
    var count by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Problem: remember loses state",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Count: $count",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Rotate your phone → count resets to 0!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { count++ }) { Text("Increment") }
        }
    }
}


// --- 15B: The Solution Pattern — State Holder ---
//
// A "State Holder" class keeps state outside the composable.
// In a real app, this would be a ViewModel.
//
// The pattern:
//   1. Create a class that holds all state for a screen
//   2. Expose state as properties
//   3. Expose functions to modify state
//   4. Composable reads state and calls functions

// This is a simplified ViewModel (same pattern, no library needed)
class TodoStateHolder {
    // State — the composable reads these
    var todos = mutableStateListOf(
        "Buy groceries",
        "Learn Compose",
        "Build an app"
    )
        private set     // Only this class can replace the list

    var newTodoText = mutableStateOf("")
        private set

    // Functions — the composable calls these
    fun onNewTodoTextChange(text: String) {
        newTodoText.value = text
    }

    fun addTodo() {
        if (newTodoText.value.isNotBlank()) {
            todos.add(newTodoText.value.trim())
            newTodoText.value = ""
        }
    }

    fun deleteTodo(index: Int) {
        if (index in todos.indices) {
            todos.removeAt(index)
        }
    }
}

@Composable
fun ViewModelPatternDemo() {
    // In real app: val stateHolder = viewModel<TodoViewModel>()
    // viewModel() survives rotation. remember {} does not.
    val stateHolder = remember { TodoStateHolder() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "ViewModel Pattern: Todo List",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // Input row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = stateHolder.newTodoText.value,
                    onValueChange = { stateHolder.onNewTodoTextChange(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("New todo...") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { stateHolder.addTodo() }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }

            // Todo list
            stateHolder.todos.forEachIndexed { index, todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "• $todo",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(onClick = { stateHolder.deleteTodo(index) }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Text(
                "In a real app, replace 'remember { TodoStateHolder() }'\n" +
                "with 'viewModel<TodoViewModel>()' to survive rotation.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


// --- 15C: State Hoisting Pattern ---
//
// STATE HOISTING = moving state UP to the parent.
//
// WHY?
//   - Makes composables REUSABLE (they don't own state)
//   - Parent controls the behavior
//   - Easier to test
//
// THE PATTERN:
//   Before (state inside):
//     fun Counter() {
//         var count by remember { mutableStateOf(0) }  ← owns state
//         Text("$count")
//         Button(onClick = { count++ })                ← owns logic
//     }
//
//   After (state hoisted to parent):
//     fun Counter(count: Int, onIncrement: () -> Unit) {  ← receives state
//         Text("$count")
//         Button(onClick = onIncrement)                    ← calls parent's logic
//     }
//
// Analogy: A TV remote (composable) doesn't decide what channel to show.
//          It just shows buttons. When you press a button, it tells the TV
//          (parent) which button was pressed. The TV decides what to do.

@Composable
fun StateHoistingDemo() {
    // PARENT owns the state
    var likes by remember { mutableIntStateOf(0) }
    var isLiked by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "State Hoisting: Parent owns state, children receive it",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // CHILD receives state + callbacks
        // The child doesn't know WHERE the state is stored
        // It just shows what it's told and calls back when clicked
        LikeCounter(
            count = likes,
            isLiked = isLiked,
            onLikeClick = {
                isLiked = !isLiked
                likes += if (isLiked) 1 else -1
            }
        )

        // Another child using the SAME state!
        // Because the parent owns it, multiple children can share it
        Text(
            "Total likes from parent's perspective: $likes",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// HOISTED composable — stateless! It receives everything from parent.
@Composable
fun LikeCounter(
    count: Int,               // State flows DOWN (parent → child)
    isLiked: Boolean,
    onLikeClick: () -> Unit   // Events flow UP (child → parent)
) {
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
        Text("Great post!", modifier = Modifier.weight(1f))
        IconButton(onClick = onLikeClick) {
            Icon(
                if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Like",
                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.outline
            )
        }
        Text("$count", style = MaterialTheme.typography.bodyMedium)
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 16: Recomposition — When & Why Compose Redraws
// ═══════════════════════════════════════════════════════════
//
// RECOMPOSITION = Compose re-running a @Composable function
//                  to update the UI with new state.
//
// WHEN does it happen?
//   When a STATE value that a composable READS changes.
//
//   var name by remember { mutableStateOf("Alex") }
//   Text(name)   ← reads "name"
//   name = "Bob"  ← "name" changed!
//                  → Compose reruns Text() with "Bob"
//                  → Screen updates!
//
// KEY RULES:
//
// 1. ONLY composables that READ the changed state recompose
//    ┌─────────────────────┐
//    │ Parent              │  ← if count changes...
//    │  ┌───────────────┐  │
//    │  │ Text(count)   │  │  ← this RECOMPOSES (reads count)
//    │  └───────────────┘  │
//    │  ┌───────────────┐  │
//    │  │ Text("Hello") │  │  ← this does NOT recompose (doesn't read count)
//    │  └───────────────┘  │
//    └─────────────────────┘
//
// 2. Recomposition can happen ANYTIME, in ANY ORDER
//    Never depend on recomposition order or count.
//    Never do side effects (network calls, logging) directly in a composable.
//
// 3. Compose is SMART — it skips unchanged composables
//    If a composable's inputs haven't changed, Compose skips it entirely.
//    This is why Compose is fast!
//
// 4. Compose compares by VALUE
//    data class User(val name: String)
//    val user1 = User("Alex")
//    val user2 = User("Alex")
//    user1 == user2 → true → Compose SKIPS recomposition!
//
// ═══════════════════════════════════════════════════════════


// --- 16A: See Recomposition in Action ---

@Composable
fun RecompositionDemo() {
    var count by remember { mutableIntStateOf(0) }
    var text by remember { mutableStateOf("Hello") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Recomposition Demo",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // This composable reads "count" → recomposes when count changes
        CountDisplay(count = count)

        // This composable reads "text" → recomposes when text changes
        TextDisplay(text = text)

        // This composable reads NOTHING → never recomposes!
        StaticDisplay()

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { count++ }) { Text("Count+") }
            Button(onClick = { text += "!" }) { Text("Add !") }
        }

        Text(
            "Click 'Count+' → only CountDisplay recomposes\n" +
            "Click 'Add !' → only TextDisplay recomposes\n" +
            "StaticDisplay never recomposes (reads no state)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun CountDisplay(count: Int) {
    // This recomposes ONLY when 'count' changes
    Text(
        "Count: $count",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun TextDisplay(text: String) {
    // This recomposes ONLY when 'text' changes
    Text(
        "Text: $text",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}

@Composable
fun StaticDisplay() {
    // This reads NO state → NEVER recomposes after first draw
    Text(
        "I never change (no state dependency)",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )
}


// --- 16B: Common Recomposition Mistakes ---

@Composable
fun RecompositionMistakes() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Common Mistakes to Avoid",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.error
        )

        MistakeItem(
            mistake = "Creating objects inside composable",
            bad = "val list = listOf(1,2,3)  // recreated every recomposition!",
            good = "val list = remember { listOf(1,2,3) }  // created once"
        )

        MistakeItem(
            mistake = "Heavy work during recomposition",
            bad = "val sorted = bigList.sortedBy { it.name }  // sorts every redraw!",
            good = "val sorted = remember(bigList) { bigList.sortedBy { it.name } }"
        )

        MistakeItem(
            mistake = "Not using keys in lists",
            bad = "items(list) { ... }  // Compose may confuse items when list changes",
            good = "items(list, key = { it.id }) { ... }  // each item has unique ID"
        )
    }
}

@Composable
fun MistakeItem(mistake: String, bad: String, good: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(mistake, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "✗ $bad",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
        Text(
            "✓ $good",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


// --- 16C: remember(key) — Recalculate When Key Changes ---
//
// remember { } → calculates once, keeps forever
// remember(key) { } → recalculates when key changes
//
// Example:
//   remember(searchQuery) { list.filter { it.contains(searchQuery) } }
//   → recalculates filtered list only when searchQuery changes
//   → NOT every recomposition!

@Composable
fun RememberKeyDemo() {
    val allItems = listOf("Apple", "Banana", "Avocado", "Blueberry",
                           "Cherry", "Apricot", "Blackberry", "Coconut")
    var searchQuery by remember { mutableStateOf("") }

    // remember(searchQuery) → only recalculates when searchQuery changes
    // Without the key, it would recalculate on EVERY recomposition
    val filteredItems = remember(searchQuery) {
        if (searchQuery.isEmpty()) allItems
        else allItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "remember(key) Demo",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Filter fruits...") },
            singleLine = true
        )

        Text(
            "${filteredItems.size} results",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )

        filteredItems.forEach { item ->
            Text(
                "• $item",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN
// ═══════════════════════════════════════════════════════════

@Composable
fun Level5Screen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── TOPIC 14: Full Screen Patterns ──
        item { L5SectionTitle("Topic 14A: Settings Screen") }
        item { SettingsScreen() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 14B: Shopping Cart") }
        item { ShoppingCartScreen() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 14C: Search Screen") }
        item { SearchScreen() }

        item { L5Divider() }

        // ── TOPIC 15: ViewModel + Compose ──
        item { L5SectionTitle("Topic 15A: The Problem") }
        item { RememberProblemDemo() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 15B: ViewModel Pattern") }
        item { ViewModelPatternDemo() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 15C: State Hoisting") }
        item { StateHoistingDemo() }

        item { L5Divider() }

        // ── TOPIC 16: Recomposition ──
        item { L5SectionTitle("Topic 16A: Recomposition Demo") }
        item { RecompositionDemo() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 16B: Common Mistakes") }
        item { RecompositionMistakes() }

        item { L5Divider() }

        item { L5SectionTitle("Topic 16C: remember(key)") }
        item { RememberKeyDemo() }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}


// ── Helpers ──

@Composable
private fun L5SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun L5Divider() {
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

@Preview(showBackground = true, name = "Level 5 - Full Screen")
@Composable
fun Level5ScreenPreview() {
    GoogleDesignTheme {
        Level5Screen()
    }
}

@Preview(showBackground = true, name = "Settings Screen")
@Composable
fun SettingsScreenPreview() {
    GoogleDesignTheme {
        SettingsScreen()
    }
}

@Preview(showBackground = true, name = "Shopping Cart")
@Composable
fun ShoppingCartPreview() {
    GoogleDesignTheme {
        ShoppingCartScreen()
    }
}
