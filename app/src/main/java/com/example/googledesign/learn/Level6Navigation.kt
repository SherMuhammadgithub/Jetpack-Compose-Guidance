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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ╔════════════════════════════════════════════════════════════╗
// ║        LEVEL 6: NAVIGATION COMPOSE — DEEP DIVE            ║
// ║                                                           ║
// ║  You already know the basics from Level 4 & MainApp.     ║
// ║  Now let's learn REAL navigation patterns you'll use     ║
// ║  in production apps.                                      ║
// ║                                                           ║
// ║  Topic 17: Passing Arguments (String, Int, Optional)     ║
// ║  Topic 18: Back Stack Control (popUpTo, launchSingleTop) ║
// ║  Topic 19: Bottom Navigation with NavHost                 ║
// ║  Topic 20: Returning Data Between Screens                 ║
// ║  Topic 21: Nested Navigation Graphs                       ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// BIG PICTURE: HOW NAVIGATION COMPOSE WORKS
// ═══════════════════════════════════════════════════════════
//
// Navigation Compose has 3 parts:
//
// 1. NavController — the "brain"
//    Knows where you are, where you've been, how to go forward/back.
//    val navController = rememberNavController()
//
// 2. NavHost — the "screen container"
//    Shows ONE screen at a time, switches based on route.
//    NavHost(navController, startDestination = "home") { ... }
//
// 3. Routes — the "addresses" (like URLs)
//    Each screen has a unique string ID.
//    "home"                  → HomeScreen
//    "profile/{userId}"      → ProfileScreen (with userId param)
//    "settings?theme=dark"   → SettingsScreen (with optional theme)
//
// THE BACK STACK:
//   Every time you navigate, the new screen is PUSHED onto a stack.
//   Pressing back POPS the top screen off the stack.
//
//   Start:        navigate("profile"):    navigate("settings"):   popBackStack():
//   [home]        [home, profile]         [home, profile, set]    [home, profile]
//                                                                  (settings removed)
//
// ═══════════════════════════════════════════════════════════
// IMPORTANT: This level uses a SUB-NavController inside itself.
// That means: the outer app navigation (home ↔ level) still works,
// and INSIDE this level we have our OWN mini-app with its own screens.
// ═══════════════════════════════════════════════════════════


@Composable
fun Level6Screen(modifier: Modifier = Modifier) {
    // Scrollable column of topic sections
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        L6SectionTitle("Topic 17: Passing Arguments")
        TopicIntro(
            "Routes can carry data. \"profile/{userId}\" means userId " +
            "is a placeholder that will be filled with a real value."
        )
        PassingArgumentsDemo()

        L6Divider()

        L6SectionTitle("Topic 18: Back Stack Control")
        TopicIntro(
            "By default, navigate() pushes a new screen on top. But sometimes " +
            "you want to replace screens (login → home), clear the stack, or " +
            "prevent duplicates. popUpTo and launchSingleTop control this."
        )
        BackStackDemo()

        L6Divider()

        L6SectionTitle("Topic 19: Bottom Nav with NavHost")
        TopicIntro(
            "Real apps combine NavHost with a NavigationBar. Each tab is a " +
            "separate route. The NavHost swaps content when you tap a tab."
        )
        BottomNavDemo()

        L6Divider()

        L6SectionTitle("Topic 20: Returning Data")
        TopicIntro(
            "Screen A opens Screen B. Screen B picks something. How does A " +
            "get the result? Use SavedStateHandle on the back stack entry."
        )
        ReturnDataDemo()

        L6Divider()

        L6SectionTitle("Topic 21: Nested Graphs")
        TopicIntro(
            "Instead of one giant NavHost, you can group related screens into " +
            "sub-graphs. Great for: auth flow, onboarding, settings section."
        )
        NestedGraphDemo()

        Spacer(modifier = Modifier.height(32.dp))
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 17: Passing Arguments
// ═══════════════════════════════════════════════════════════
//
// SYNTAX:
//   Route with arg:       "profile/{userId}"
//   Navigate with value:  navController.navigate("profile/42")
//   Read the value:       backStackEntry.arguments?.getString("userId")
//
// TYPED arguments:
//   composable(
//       route = "profile/{userId}",
//       arguments = listOf(navArgument("userId") { type = NavType.IntType })
//   ) { entry ->
//       val id = entry.arguments?.getInt("userId") ?: 0
//   }
//
// OPTIONAL arguments (query params):
//   Route:     "search?query={query}"
//   Navigate:  navController.navigate("search?query=compose")
//   Arg defn:  navArgument("query") { defaultValue = ""; nullable = true }
// ═══════════════════════════════════════════════════════════

@Composable
fun PassingArgumentsDemo() {
    // Each demo gets its OWN sub-NavController
    val navController = rememberNavController()

    DemoContainer {
        NavHost(
            navController = navController,
            startDestination = "users"
        ) {
            // Screen 1: User list
            composable("users") {
                UserListScreen(
                    onUserClick = { userId, userName ->
                        // Pass TWO arguments in the route
                        // Note: we URL-encode spaces by replacing with "_"
                        navController.navigate("user_detail/$userId/${userName.replace(" ", "_")}")
                    }
                )
            }

            // Screen 2: User detail — receives TWO arguments
            composable(
                route = "user_detail/{userId}/{userName}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("userName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                // Extract the arguments
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                val userName = backStackEntry.arguments?.getString("userName")?.replace("_", " ") ?: "Unknown"

                UserDetailScreen(
                    userId = userId,
                    userName = userName,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun UserListScreen(onUserClick: (Int, String) -> Unit) {
    val users = listOf(
        1 to "Alice Johnson",
        2 to "Bob Smith",
        3 to "Charlie Brown"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Users", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        users.forEach { (id, name) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUserClick(id, name) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$id", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                Text("Tap →", color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun UserDetailScreen(userId: Int, userName: String, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("User Detail", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))

        // These values came from the ROUTE!
        Text("ID: $userId", style = MaterialTheme.typography.bodyLarge)
        Text("Name: $userName", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "These values were passed through the route:\n" +
            "\"user_detail/$userId/${userName.replace(" ", "_")}\"",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 18: Back Stack Control
// ═══════════════════════════════════════════════════════════
//
// Default navigate() behavior: PUSH onto the stack.
//   navigate("login")   → [home, login]
//   navigate("signup")  → [home, login, signup]
//
// PROBLEM 1: Duplicates
//   User taps the same tab 5 times → [home, home, home, home, home]
//   FIX: launchSingleTop = true → only one instance ever
//
// PROBLEM 2: Auth flow
//   Login success → navigate("home") → stack is [login, home]
//   User presses back → goes BACK to login! BAD!
//   FIX: popUpTo("login") { inclusive = true }
//        → removes login from stack before navigating
//
// PROBLEM 3: Clear all and go home (logout)
//   FIX: popUpTo(navController.graph.startDestinationId) { inclusive = false }
// ═══════════════════════════════════════════════════════════

@Composable
fun BackStackDemo() {
    val navController = rememberNavController()

    // Show current back stack at the top for visualization
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route ?: "login"

    DemoContainer {
        Column {
            // Back stack visualization
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                Text(
                    "Current screen: $currentRoute",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Login Screen", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Scenario: After login we go to 'home'. But we DON'T " +
                            "want back button to come back here.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(onClick = {
                            // popUpTo("login") { inclusive = true } →
                            // Before navigating to "home", remove "login" from stack.
                            // So after this: stack = [home] (NOT [login, home])
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }) {
                            Text("Login → Home (clean stack)")
                        }
                    }
                }

                composable("home") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Home Screen", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Back button now EXITS this demo instead of going " +
                            "back to login. Exactly what we want.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(onClick = {
                            // launchSingleTop = true →
                            // If "profile" is already on top of stack, don't push
                            // another copy. Tap 10 times → still only 1 instance.
                            navController.navigate("profile") {
                                launchSingleTop = true
                            }
                        }) {
                            Text("Go to Profile (singleTop)")
                        }
                    }
                }

                composable("profile") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Profile Screen", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tap 'Logout' to clear ALL screens and return to login.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { navController.popBackStack() }) {
                                Text("Back")
                            }

                            Button(onClick = {
                                // LOGOUT pattern: clear everything, go to login
                                // popUpTo(0) { inclusive = true } wipes the stack
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }) {
                                Text("Logout")
                            }
                        }
                    }
                }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 19: Bottom Navigation with NavHost
// ═══════════════════════════════════════════════════════════
//
// THE COMMON PATTERN:
//   Scaffold has bottomBar = NavigationBar
//   Each tab calls navController.navigate("tab_route")
//   NavHost in Scaffold's content swaps the visible screen
//
// KEY DETAILS:
//   1. Use saveState/restoreState to keep scroll positions
//   2. Use launchSingleTop to prevent duplicate tab entries
//   3. Use popUpTo with the start destination to avoid giant stack
//
// This demo has 3 tabs: Home, Search, Notifications
// ═══════════════════════════════════════════════════════════

@Composable
fun BottomNavDemo() {
    val navController = rememberNavController()

    DemoContainer {
        Column {
            // Content area
            Box(modifier = Modifier.weight(1f)) {
                NavHost(
                    navController = navController,
                    startDestination = "tab_home"
                ) {
                    composable("tab_home") { TabContent("Home", Icons.Filled.Home) }
                    composable("tab_search") { TabContent("Search", Icons.Filled.Search) }
                    composable("tab_notifications") { TabContent("Notifications", Icons.Filled.Notifications) }
                }
            }

            // Bottom navigation bar
            val currentEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentEntry?.destination?.route

            NavigationBar {
                listOf(
                    Triple("tab_home", "Home", Icons.Filled.Home),
                    Triple("tab_search", "Search", Icons.Filled.Search),
                    Triple("tab_notifications", "Alerts", Icons.Filled.Notifications),
                ).forEach { (route, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                // Pop everything up to the start → clean stack
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true    // Remember scroll position
                                }
                                // Don't create duplicates when tapping same tab
                                launchSingleTop = true
                                // Restore previous state when coming back
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TabContent(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("$name Tab", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 20: Returning Data Between Screens
// ═══════════════════════════════════════════════════════════
//
// Screen A needs a value → opens Screen B → user picks something → A needs the result.
//
// SOLUTION: SavedStateHandle of the PREVIOUS back stack entry.
//
// Screen A (opens B):
//   navController.navigate("picker")
//   // Later, read the result:
//   val result = navController.currentBackStackEntry
//       ?.savedStateHandle
//       ?.getStateFlow("selected_color", "none")
//       ?.collectAsState()
//
// Screen B (returns data):
//   navController.previousBackStackEntry
//       ?.savedStateHandle
//       ?.set("selected_color", "blue")
//   navController.popBackStack()
//
// In this demo we use a simpler approach: a shared MutableState
// passed as parameter, since sub-NavControllers make SavedStateHandle tricky.
// ═══════════════════════════════════════════════════════════

@Composable
fun ReturnDataDemo() {
    val navController = rememberNavController()

    // Shared state between screens (in a real app, use SavedStateHandle or ViewModel)
    val selectedFruit = remember { mutableStateOf("None") }

    DemoContainer {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Main Screen", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Selected: ${selectedFruit.value}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { navController.navigate("picker") }) {
                        Text("Pick a Fruit")
                    }
                }
            }

            composable("picker") {
                FruitPicker(
                    onPick = { fruit ->
                        // "Return" the data by updating shared state, then pop
                        selectedFruit.value = fruit
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun FruitPicker(onPick: (String) -> Unit, onCancel: () -> Unit) {
    val fruits = listOf("Apple", "Banana", "Cherry", "Mango")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onCancel) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cancel")
            }
            Text("Pick a Fruit", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        fruits.forEach { fruit ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPick(fruit) }
                    .padding(vertical = 10.dp)
            ) {
                Text("• $fruit", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 21: Nested Navigation Graphs
// ═══════════════════════════════════════════════════════════
//
// Instead of ONE giant NavHost with 20 routes, group related ones.
//
// Example: Auth flow (login, signup, forgot password) and
//          Main app (home, profile, settings) are two groups.
//
// SYNTAX:
//   NavHost(navController, startDestination = "auth_graph") {
//       navigation(startDestination = "login", route = "auth_graph") {
//           composable("login") { ... }
//           composable("signup") { ... }
//       }
//       navigation(startDestination = "home", route = "main_graph") {
//           composable("home") { ... }
//           composable("profile") { ... }
//       }
//   }
//
// Navigate to a graph:
//   navController.navigate("main_graph")  → goes to home (the start destination)
//
// BENEFITS:
//   - Organized code
//   - Can share ViewModels within a graph
//   - Easier to reason about flows
// ═══════════════════════════════════════════════════════════

@Composable
fun NestedGraphDemo() {
    val navController = rememberNavController()

    DemoContainer {
        NavHost(
            navController = navController,
            startDestination = "auth_graph"
        ) {
            // ── AUTH GRAPH — login/signup flow ──
            navigation(
                startDestination = "login_screen",
                route = "auth_graph"
            ) {
                composable("login_screen") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Auth Graph", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text("Login", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { navController.navigate("signup_screen") }) {
                                Text("Signup")
                            }
                            Button(onClick = {
                                // Jump from auth_graph to main_graph
                                navController.navigate("main_graph") {
                                    popUpTo("auth_graph") { inclusive = true }
                                }
                            }) {
                                Text("Login →")
                            }
                        }
                    }
                }

                composable("signup_screen") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Auth Graph", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text("Signup", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = { navController.popBackStack() }) {
                            Text("Back to Login")
                        }
                    }
                }
            }

            // ── MAIN GRAPH — home/settings ──
            navigation(
                startDestination = "home_screen",
                route = "main_graph"
            ) {
                composable("home_screen") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Main Graph", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
                        Text("Home", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { navController.navigate("settings_screen") }) {
                                Text("Settings →")
                            }
                            OutlinedButton(onClick = {
                                navController.navigate("auth_graph") {
                                    popUpTo("main_graph") { inclusive = true }
                                }
                            }) {
                                Text("Logout")
                            }
                        }
                    }
                }

                composable("settings_screen") {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Main Graph", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
                        Text("Settings", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = { navController.popBackStack() }) {
                            Text("Back to Home")
                        }
                    }
                }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// HELPERS
// ═══════════════════════════════════════════════════════════

@Composable
private fun DemoContainer(content: @Composable () -> Unit) {
    // A fixed-size "phone frame" so each demo has a clear boundary
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        content()
    }
}

@Composable
private fun L6SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun TopicIntro(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun L6Divider() {
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

@Preview(showBackground = true, name = "Level 6 - Full")
@Composable
fun Level6ScreenPreview() {
    GoogleDesignTheme {
        Level6Screen()
    }
}
