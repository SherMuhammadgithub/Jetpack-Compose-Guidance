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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
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
// ║          LEVEL 4: LISTS & NAVIGATION                      ║
// ║                                                           ║
// ║  Topic 11: LazyColumn & LazyRow (scrollable lists)       ║
// ║  Topic 12: Cards & ListItems (Material 3 containers)     ║
// ║  Topic 13: Navigation (moving between screens)           ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// TOPIC 11: LazyColumn & LazyRow
// ═══════════════════════════════════════════════════════════
//
// THE PROBLEM:
//   In Level 1, we used Column with verticalScroll to scroll.
//   But what if you have 1000 items? Column creates ALL 1000
//   at once — super slow and wastes memory!
//
// THE SOLUTION: LazyColumn / LazyRow
//   "Lazy" means: only create items that are VISIBLE on screen.
//   As you scroll, new items are created and off-screen items
//   are destroyed. Just like RecyclerView in XML!
//
//   Column (all at once):          LazyColumn (only visible):
//   ┌──────────┐                  ┌──────────┐
//   │ Item 1   │ ← created       │          │
//   │ Item 2   │ ← created       │ visible  │
//   │ Item 3   │ ← VISIBLE       │ items    │
//   │ Item 4   │ ← VISIBLE       │ only!    │
//   │ Item 5   │ ← VISIBLE       │          │
//   │ Item 6   │ ← created       └──────────┘
//   │ Item 7   │ ← created       Items 1,2,6,7 don't exist yet!
//   │ ...      │ ← created       They're created when you scroll to them.
//   │ Item 999 │ ← created
//   └──────────┘
//   ALL 999 created = SLOW!       Only ~5 created = FAST!
//
// SYNTAX:
//   LazyColumn {
//       items(myList) { item ->     ← loops through each item
//           Text(item.name)         ← composable for each item
//       }
//   }
//
// KEY RULE: Inside LazyColumn { }, you can ONLY use:
//   item { }         → single item
//   items(list) { }  → loop through a list
//   itemsIndexed()   → loop with index number
//   stickyHeader { } → header that sticks to top
//
// You CANNOT put regular composables directly inside LazyColumn { }.
// Use item { } to wrap them:
//   LazyColumn {
//       item { Text("Header") }       ← single item
//       items(myList) { ... }          ← list of items
//       item { Text("Footer") }       ← single item
//   }
// ═══════════════════════════════════════════════════════════


// --- 11A: Simplest LazyColumn ---

@Composable
fun SimpleLazyColumn() {
    // A basic list of 50 items
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),       // Fixed height so it doesn't take full screen

        // contentPadding = padding around the ENTIRE list
        // (like padding on the scrollable area)
        contentPadding = PaddingValues(8.dp),

        // verticalArrangement = gap between items (just like Column!)
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // items(count) → creates 'count' items with index
        items(50) { index ->
            Text(
                text = "Item #${index + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            )
        }
    }
}


// --- 11B: LazyColumn with a List of Data ---

// First, let's create some sample data
data class Contact(
    val name: String,
    val email: String,
    val initial: String     // First letter for avatar
)

val sampleContacts = listOf(
    Contact("Alice Johnson", "alice@email.com", "A"),
    Contact("Bob Smith", "bob@email.com", "B"),
    Contact("Charlie Brown", "charlie@email.com", "C"),
    Contact("Diana Prince", "diana@email.com", "D"),
    Contact("Edward Norton", "edward@email.com", "E"),
    Contact("Fiona Apple", "fiona@email.com", "F"),
    Contact("George Lucas", "george@email.com", "G"),
    Contact("Hannah Montana", "hannah@email.com", "H"),
    Contact("Ivan Drago", "ivan@email.com", "I"),
    Contact("Julia Roberts", "julia@email.com", "J"),
)

@Composable
fun ContactList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // HEADER — a single item at the top
        item {
            Text(
                "My Contacts (${sampleContacts.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }

        // LIST ITEMS — loops through the contacts
        items(sampleContacts) { contact ->
            // 'contact' is each Contact object from the list
            ContactRow(contact = contact)
        }

        // FOOTER — a single item at the bottom
        item {
            Text(
                "End of list",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// A reusable composable for each contact row
@Composable
fun ContactRow(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* handle click */ }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                contact.initial,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                contact.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                contact.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(20.dp)
        )
    }
}


// --- 11C: LazyColumn with itemsIndexed ---
//
// Sometimes you need the INDEX number (0, 1, 2...) along with the item.
// itemsIndexed gives you both!

@Composable
fun IndexedList() {
    val fruits = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry",
                         "Fig", "Grape", "Honeydew")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(fruits) { index, fruit ->
            // index = 0, 1, 2...
            // fruit = "Apple", "Banana"...
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (index % 2 == 0) MaterialTheme.colorScheme.surfaceVariant
                        else MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rank number
                Text(
                    "#${index + 1}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(40.dp)
                )
                Text(fruit, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


// --- 11D: LazyRow — Horizontal Scrolling List ---
//
// Same as LazyColumn but scrolls HORIZONTALLY.
// Perfect for: story circles, category chips, image galleries

@Composable
fun HorizontalStoryList() {
    val people = listOf("You", "Alice", "Bob", "Charlie", "Diana",
                        "Edward", "Fiona", "George", "Hannah")

    Column {
        Text(
            "Stories",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
        )

        LazyRow(
            // contentPadding adds space at start and end of the row
            contentPadding = PaddingValues(horizontal = 12.dp),
            // horizontalArrangement = gap between items
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(people) { person ->
                // Each story circle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                if (person == "You") MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.secondaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (person == "You") {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add story",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            Text(
                                person.first().toString(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        person,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                }
            }
        }
    }
}


// --- 11E: LazyRow — Category Chips ---

@Composable
fun CategoryChips() {
    val categories = listOf("All", "Design", "Coding", "Music",
                             "Sports", "Travel", "Food", "Tech")
    var selected by remember { mutableStateOf("All") }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (selected == category) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .clickable { selected = category }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = if (selected == category) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


// --- 11F: Dynamic List (Add/Remove items) ---
//
// mutableStateListOf = a list that Compose WATCHES for changes.
// When you add/remove items, Compose automatically updates the UI!
//
// Regular list:        mutableStateListOf:
// val list = listOf()  val list = mutableStateListOf()
// Can't change it!     list.add(), list.remove() → UI updates!

@Composable
fun DynamicList() {
    // mutableStateListOf = Compose-aware mutable list
    val items = remember { mutableStateListOf("Learn Compose", "Build an app", "Get a job") }
    var nextId by remember { mutableIntStateOf(4) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header with Add button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Todo List (${items.size})", style = MaterialTheme.typography.titleMedium)
            Button(onClick = {
                items.add("New task #$nextId")
                nextId++
            }) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // The list — automatically updates when items change!
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            itemsIndexed(items) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        item,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    // Delete button
                    IconButton(onClick = { items.removeAt(index) }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 12: Cards & ListItems — Material 3 Containers
// ═══════════════════════════════════════════════════════════
//
// CARD = a container with shape + shadow + background.
// Think of it like a piece of paper floating above the background.
//
// Material 3 has 3 card types:
//   Card()         → filled background (default)
//   ElevatedCard() → shadow elevation (floats above)
//   OutlinedCard() → just a border (flat)
//
// LISTITEM = a pre-built row layout for lists.
// Instead of building Row + Avatar + Column manually,
// ListItem gives you: headlineContent, supportingContent,
// leadingContent, trailingContent — all positioned correctly.
// ═══════════════════════════════════════════════════════════


// --- 12A: Card Types ---

@Composable
fun CardTypes() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // FILLED Card (default) — has background color
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Filled Card", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Default card with filled background color.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ELEVATED Card — has shadow
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Elevated Card", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Has shadow elevation — looks like it's floating.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // OUTLINED Card — just border, no fill or shadow
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Outlined Card", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Just a border outline — flat and minimal.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// --- 12B: Clickable Card ---

@Composable
fun ClickableCards() {
    var selectedCard by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Option A", "Option B", "Option C").forEach { option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedCard = option },
                shape = RoundedCornerShape(12.dp),
                // Change color when selected!
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedCard == option)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Radio-style indicator
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedCard == option) MaterialTheme.colorScheme.error
                                else Color.Transparent
                            )
                            .then(
                                if (selectedCard != option)
                                    Modifier.background(
                                        Color.Transparent,
                                        CircleShape
                                    )
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedCard == option) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onPrimary)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(option, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        if (selectedCard.isNotEmpty()) {
            Text(
                "Selected: $selectedCard",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


// --- 12C: ListItem — Pre-built List Row ---
//
// ListItem is a Material 3 component that gives you a
// perfectly spaced list row without manual layout.
//
// Structure:
//   ┌──────────────────────────────────────────────┐
//   │  [leading]  Headline Text        [trailing]  │
//   │             Supporting text                   │
//   └──────────────────────────────────────────────┘
//
// Instead of building Row + Spacer + Column yourself,
// ListItem handles all the spacing automatically!

@Composable
fun ListItemExamples() {
    Column {

        // Simple ListItem
        ListItem(
            headlineContent = { Text("Simple item") },
            supportingContent = { Text("With supporting text") }
        )

        HorizontalDivider()

        // ListItem with leading icon
        ListItem(
            headlineContent = { Text("Settings") },
            supportingContent = { Text("App preferences") },
            leadingContent = {
                Icon(Icons.Filled.Settings, contentDescription = null)
            }
        )

        HorizontalDivider()

        // ListItem with leading avatar + trailing action
        ListItem(
            headlineContent = { Text("Alice Johnson") },
            supportingContent = { Text("Online now") },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A", color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            },
            trailingContent = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        )

        HorizontalDivider()

        // ListItem with trailing text
        ListItem(
            headlineContent = { Text("Notifications") },
            supportingContent = { Text("3 unread messages") },
            leadingContent = {
                Icon(Icons.Filled.Notifications, contentDescription = null)
            },
            trailingContent = {
                Text(
                    "3",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}


// --- 12D: Card + LazyColumn Combined (Real-World Pattern) ---

data class Article(
    val title: String,
    val description: String,
    val author: String,
    val likes: Int
)

val sampleArticles = listOf(
    Article("Getting Started with Compose", "Learn the basics of Jetpack Compose in this beginner-friendly guide.", "Alice", 42),
    Article("Material 3 Design System", "Explore Google's latest design system and how to implement it.", "Bob", 38),
    Article("State Management Guide", "Understanding remember, mutableStateOf, and recomposition.", "Charlie", 55),
    Article("LazyColumn Best Practices", "Tips for building performant scrollable lists in Compose.", "Diana", 29),
    Article("Navigation in Compose", "How to move between screens using the Navigation component.", "Edward", 31),
)

@Composable
fun ArticleCard(article: Article) {
    var isLiked by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                article.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                article.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Bottom row: author + like button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "By ${article.author}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { isLiked = !isLiked }) {
                        Icon(
                            if (isLiked) Icons.Filled.Favorite
                            else Icons.Filled.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red
                                   else MaterialTheme.colorScheme.outline
                        )
                    }
                    Text(
                        "${article.likes + if (isLiked) 1 else 0}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleFeed() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleArticles) { article ->
            ArticleCard(article = article)
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 13: Navigation — Moving Between Screens
// ═══════════════════════════════════════════════════════════
//
// In real apps, you'd use "Navigation Compose" library:
//   implementation("androidx.navigation:navigation-compose:2.8.x")
//
// But FIRST, let's understand the CONCEPT using simple state.
// This teaches you the PATTERN before adding the library.
//
// The idea:
//   1. You have a state variable: var currentScreen by remember { ... }
//   2. Based on that state, you show different composables
//   3. Buttons/clicks change the state → screen changes!
//
// This is actually how navigation works under the hood:
//   currentScreen = "home" → show HomeScreen()
//   currentScreen = "profile" → show ProfileScreen()
//
// ═══════════════════════════════════════════════════════════


// --- 13A: Simple Screen Switching ---

@Composable
fun SimpleNavigation() {
    // This state controls which "screen" is showing
    var currentScreen by remember { mutableStateOf("home") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        // CONTENT AREA — changes based on currentScreen
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (currentScreen) {
                "home" -> HomeContent()
                "search" -> SearchContent()
                "profile" -> ProfileContent()
            }
        }

        // BOTTOM NAVIGATION BAR
        // This is always visible — only the content above changes
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentScreen == "home",
                onClick = { currentScreen = "home" }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                label = { Text("Search") },
                selected = currentScreen == "search",
                onClick = { currentScreen = "search" }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = currentScreen == "profile",
                onClick = { currentScreen = "profile" }
            )
        }
    }
}

// The "screens" — in a real app, these would be in separate files
@Composable
fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Home,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Home Screen", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Welcome back!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SearchContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Search Screen", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Find what you need",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Person,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Profile Screen", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Your account details",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// --- 13B: Scaffold with TopAppBar + FAB + BottomNav ---
//
// Scaffold is the SKELETON of a screen. It positions:
//   - TopAppBar (top)
//   - BottomBar (bottom)
//   - FloatingActionButton (corner)
//   - Content (middle)
//
//   ┌──────────────────────────┐
//   │  TopAppBar               │
//   ├──────────────────────────┤
//   │                          │
//   │     Content              │
//   │                     [FAB]│  ← Floating Action Button
//   │                          │
//   ├──────────────────────────┤
//   │  BottomNavigationBar     │
//   └──────────────────────────┘

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScaffoldExample() {
    var currentTab by remember { mutableStateOf("home") }
    var fabClickCount by remember { mutableIntStateOf(0) }

    // IMPORTANT: Scaffold needs a bounded height!
    // Inside a LazyColumn item, there is no max height by default.
    // So we wrap it in a fixed-height Box. Without this → CRASH!
    Scaffold(
        modifier = Modifier.height(400.dp),
        // TOP APP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentTab) {
                            "home" -> "Home"
                            "favorites" -> "Favorites"
                            "profile" -> "Profile"
                            else -> "App"
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                // Navigation icon (back arrow)
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Action icons (top-right)
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },

        // BOTTOM NAVIGATION BAR
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Home") },
                    selected = currentTab == "home",
                    onClick = { currentTab = "home" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("Favorites") },
                    selected = currentTab == "favorites",
                    onClick = { currentTab = "favorites" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("Profile") },
                    selected = currentTab == "profile",
                    onClick = { currentTab = "profile" }
                )
            }
        },

        // FLOATING ACTION BUTTON
        floatingActionButton = {
            FloatingActionButton(
                onClick = { fabClickCount++ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }

    ) { innerPadding ->
        // CONTENT — uses innerPadding to avoid overlapping bars!
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Tab: ${currentTab.replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.headlineMedium
                )
                if (fabClickCount > 0) {
                    Text(
                        "FAB clicked $fabClickCount times",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


// --- 13C: Master-Detail Pattern (List → Detail) ---
//
// A very common pattern: tap an item in a list → see its details.
// Again, we just use STATE to switch between views!

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val rating: Float
)

val sampleProducts = listOf(
    Product(1, "Pixel Phone", "$699", "Google's latest smartphone with amazing camera.", 4.5f),
    Product(2, "Pixel Watch", "$349", "Sleek smartwatch with Fitbit integration.", 4.2f),
    Product(3, "Pixel Buds", "$199", "Wireless earbuds with real-time translation.", 4.0f),
    Product(4, "Pixel Tablet", "$499", "A tablet that doubles as a smart display.", 4.3f),
    Product(5, "Nest Hub", "$99", "Smart display for your home.", 4.6f),
)

@Composable
fun MasterDetailDemo() {
    // null = show list, non-null = show detail
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        if (selectedProduct == null) {
            // MASTER (list) view
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        "Google Store",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(sampleProducts) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedProduct = product },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    product.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    product.price,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "View details",
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        } else {
            // DETAIL view
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Back button
                Button(onClick = { selectedProduct = null }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Back to list")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    selectedProduct!!.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    selectedProduct!!.price,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Star rating
                Row {
                    repeat(5) { index ->
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (index < selectedProduct!!.rating.toInt())
                                Color(0xFFFFD700) else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "${selectedProduct!!.rating}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    selectedProduct!!.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN
// ═══════════════════════════════════════════════════════════

@Composable
fun Level4Screen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── TOPIC 11: LazyColumn & LazyRow ──
        item { L4SectionTitle("Topic 11A: Simple LazyColumn") }
        item { SimpleLazyColumn() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 11B: Contact List") }
        item { ContactList() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 11C: Indexed List") }
        item { IndexedList() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 11D: LazyRow — Stories") }
        item { HorizontalStoryList() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 11E: LazyRow — Chips") }
        item { CategoryChips() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 11F: Dynamic List (Add/Remove)") }
        item { DynamicList() }

        item { L4Divider() }

        // ── TOPIC 12: Cards & ListItems ──
        item { L4SectionTitle("Topic 12A: Card Types") }
        item { CardTypes() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 12B: Clickable Cards") }
        item { ClickableCards() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 12C: ListItem Component") }
        item { ListItemExamples() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 12D: Article Feed") }
        item { ArticleFeed() }

        item { L4Divider() }

        // ── TOPIC 13: Navigation ──
        item { L4SectionTitle("Topic 13A: Bottom Navigation") }
        item { SimpleNavigation() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 13B: Full Scaffold") }
        item { FullScaffoldExample() }

        item { L4Divider() }

        item { L4SectionTitle("Topic 13C: Master → Detail") }
        item { MasterDetailDemo() }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}


// ── Helpers ──

@Composable
private fun L4SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun L4Divider() {
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

@Preview(showBackground = true, name = "Level 4 - Full Screen")
@Composable
fun Level4ScreenPreview() {
    GoogleDesignTheme {
        Level4Screen()
    }
}

@Preview(showBackground = true, name = "Scaffold Example")
@Composable
fun ScaffoldPreview() {
    GoogleDesignTheme {
        FullScaffoldExample()
    }
}

@Preview(showBackground = true, name = "Master Detail")
@Composable
fun MasterDetailPreview() {
    GoogleDesignTheme {
        MasterDetailDemo()
    }
}
