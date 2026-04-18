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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.googledesign.ui.theme.GoogleDesignTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ╔════════════════════════════════════════════════════════════╗
// ║      LEVEL 7: VIEWMODEL + API CALLS — REAL APP DATA       ║
// ║                                                           ║
// ║  Topic 22: Why ViewModel & how it survives rotation      ║
// ║  Topic 23: StateFlow & collectAsStateWithLifecycle       ║
// ║  Topic 24: UI State pattern (Loading/Success/Error)      ║
// ║  Topic 25: Fake API calls with coroutines                 ║
// ║  Topic 26: LaunchedEffect for triggering work             ║
// ║  Topic 27: Retry logic & user actions                     ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// THE BIG PICTURE
// ═══════════════════════════════════════════════════════════
//
// In Level 5 we touched ViewModel briefly. Now let's do it RIGHT,
// the way real apps work when fetching data from an API.
//
// THE PROBLEM:
//   When you fetch data from a server:
//     1. Show a loading spinner
//     2. Call the API (takes time)
//     3. If success → show data
//     4. If fails → show error + retry button
//     5. If user rotates phone in the middle → DON'T restart the call!
//
//   Doing all this with just `remember` is painful and breaks on rotation.
//
// THE SOLUTION:
//   ViewModel + StateFlow + collectAsStateWithLifecycle
//
//   ViewModel (lives outside composable, survives rotation)
//      ↓ exposes
//   StateFlow<UiState> (a stream of UI state values)
//      ↓ collected by
//   Composable (collectAsStateWithLifecycle)
//      ↓ shows
//   Loading / Success / Error UI
//
// FLOW:
//   ┌─────────────────────────────┐
//   │       ViewModel             │
//   │                             │
//   │  state: StateFlow           │ ← updates when data arrives
//   │  fun loadData()             │ ← starts the API call
//   │     │                       │
//   │     ▼                       │
//   │  viewModelScope.launch {    │ ← coroutine scope tied to ViewModel
//   │     state = Loading          │
//   │     val data = api.fetch()   │ ← suspending call
//   │     state = Success(data)    │
//   │  }                          │
//   └─────┬───────────────────────┘
//         │ collectAsStateWithLifecycle
//         ▼
//   ┌─────────────────────────────┐
//   │       Composable            │
//   │                             │
//   │  when (state) {              │
//   │    Loading → spinner         │
//   │    Success → list of items   │
//   │    Error   → message + retry │
//   │  }                          │
//   └─────────────────────────────┘
// ═══════════════════════════════════════════════════════════


// ═══════════════════════════════════════════════════════════
// TOPIC 22 + 23 + 24: ViewModel, StateFlow, UI State
// ═══════════════════════════════════════════════════════════
//
// First, define the UI STATE as a sealed interface.
// A "sealed interface" means: there are EXACTLY these subtypes,
// no other subtype is possible. Compose can use 'when' on it
// without an 'else' branch — the compiler enforces completeness.
//
// Three cases:
//   Loading        → show spinner
//   Success(data)  → show the data
//   Error(msg)     → show error message
// ═══════════════════════════════════════════════════════════

sealed interface UsersUiState {
    data object Loading : UsersUiState
    data class Success(val users: List<FakeUser>) : UsersUiState
    data class Error(val message: String) : UsersUiState
}

data class FakeUser(
    val id: Int,
    val name: String,
    val email: String
)


// ═══════════════════════════════════════════════════════════
// THE VIEWMODEL
// ═══════════════════════════════════════════════════════════
//
// This is the BRAIN of the screen. It:
//   - Holds the UI state
//   - Knows how to load data
//   - Survives rotation
//   - Cleans up automatically when the screen is gone
//
// KEY PARTS:
//
//   class XxxViewModel : ViewModel()
//      → extends ViewModel() so Compose can manage its lifecycle
//
//   private val _uiState = MutableStateFlow(...)
//   val uiState: StateFlow<...> = _uiState.asStateFlow()
//      → backing field is mutable, exposed one is read-only
//      → this is a common pattern: "you can READ but only I can WRITE"
//
//   viewModelScope.launch { ... }
//      → coroutine scope tied to the ViewModel's life
//      → automatically cancels if the screen is destroyed
//      → no memory leaks!
// ═══════════════════════════════════════════════════════════

class UsersViewModel : ViewModel() {

    // Backing state — only this class can change it
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)

    // Public state — composables can READ but not WRITE
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    // When the ViewModel is created, start loading immediately
    init {
        loadUsers()
    }

    // Public function the UI can call (e.g., from a Retry button)
    fun loadUsers() {
        // viewModelScope = a coroutine scope owned by this ViewModel
        // It auto-cancels when the ViewModel dies (screen permanently gone)
        viewModelScope.launch {
            // Step 1: Show loading
            _uiState.value = UsersUiState.Loading

            // Step 2: Try the "API call"
            try {
                val users = fakeApiFetchUsers()    // suspending function
                _uiState.value = UsersUiState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UsersUiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

    // FAKE API CALL — pretends to be a network request
    // 'suspend' = this function pauses without blocking the thread
    // delay() = the suspending equivalent of Thread.sleep()
    private suspend fun fakeApiFetchUsers(): List<FakeUser> {
        delay(1500)    // Simulate network latency

        // Randomly fail ~25% of the time so you can see Error state
        if ((0..3).random() == 0) {
            throw Exception("Network error: timeout after 1500ms")
        }

        // Otherwise return fake data
        return listOf(
            FakeUser(1, "Alice Johnson", "alice@example.com"),
            FakeUser(2, "Bob Smith", "bob@example.com"),
            FakeUser(3, "Charlie Brown", "charlie@example.com"),
            FakeUser(4, "Diana Prince", "diana@example.com"),
            FakeUser(5, "Edward Norton", "edward@example.com"),
        )
    }
}


// ═══════════════════════════════════════════════════════════
// THE COMPOSABLE — Reads ViewModel state and renders UI
// ═══════════════════════════════════════════════════════════
//
// HOW IT WORKS:
//
// 1. viewModel<UsersViewModel>()
//    → Compose creates the ViewModel for us (or returns existing one)
//    → It survives rotation and recomposition
//
// 2. uiState.collectAsStateWithLifecycle()
//    → Subscribes to the StateFlow
//    → Updates the UI when state changes
//    → STOPS collecting when screen is in background (battery saving!)
//    → This is BETTER than collectAsState() which keeps collecting always
//
// 3. when (state) { ... }
//    → Smart cast: Kotlin knows the type inside each branch
//    → state.users in Success branch → no need for cast
// ═══════════════════════════════════════════════════════════

@Composable
fun UsersScreen() {
    // Get (or create) the ViewModel
    val viewModel: UsersViewModel = viewModel()

    // Subscribe to its state — the 'by' delegate gives us the value directly
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with refresh button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Users API",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                OutlinedButton(
                    onClick = { viewModel.loadUsers() },
                    enabled = uiState !is UsersUiState.Loading
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Refresh")
                }
            }

            // The MAIN UI — switches based on state
            // This is the HEART of the UI State pattern!
            when (val state = uiState) {
                is UsersUiState.Loading -> LoadingView()
                is UsersUiState.Success -> SuccessView(state.users)
                is UsersUiState.Error -> ErrorView(
                    message = state.message,
                    onRetry = { viewModel.loadUsers() }
                )
            }
        }
    }
}

// One composable per state — keeps the code clean

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text("Fetching users...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun SuccessView(users: List<FakeUser>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        user.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(user.name, fontWeight = FontWeight.SemiBold)
                    Text(
                        user.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                Icons.Filled.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Something went wrong",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Retry")
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 25 + 26: Search with debounce + LaunchedEffect
// ═══════════════════════════════════════════════════════════
//
// A second example: a search bar that calls an "API" as you type.
//
// PROBLEMS we solve:
//   1. Don't fetch on EVERY keystroke (waste of bandwidth)
//   2. Show loading while fetching
//   3. Cancel previous request if user types again before it finishes
//
// LaunchedEffect:
//   A coroutine that starts when its KEY changes.
//   When key changes, the previous coroutine is CANCELLED.
//
//   LaunchedEffect(query) {
//       delay(500)   // wait 500ms — if query changes again, this restarts
//       fetchResults(query)
//   }
//
// This is called "debouncing" — wait until typing stops.
// ═══════════════════════════════════════════════════════════

class SearchViewModel : ViewModel() {

    // The current search query
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // The current state of search results
    private val _searchState = MutableStateFlow<UsersUiState>(
        UsersUiState.Success(emptyList())
    )
    val searchState: StateFlow<UsersUiState> = _searchState.asStateFlow()

    fun onQueryChange(newQuery: String) {
        // Use update {} for thread-safe state changes
        _query.update { newQuery }
    }

    // Called from LaunchedEffect when query changes
    suspend fun search(query: String) {
        if (query.isBlank()) {
            _searchState.value = UsersUiState.Success(emptyList())
            return
        }

        _searchState.value = UsersUiState.Loading

        // Simulate API search latency
        delay(800)

        // Fake results
        val allItems = listOf(
            "Apple", "Banana", "Cherry", "Date", "Elderberry",
            "Fig", "Grape", "Honeydew", "Kiwi", "Lemon",
            "Mango", "Orange", "Peach", "Quince", "Raspberry"
        )

        val results = allItems
            .filter { it.contains(query, ignoreCase = true) }
            .mapIndexed { i, name -> FakeUser(i, name, "$name.fruit@example.com") }

        _searchState.value = UsersUiState.Success(results)
    }
}

@Composable
fun DebouncedSearchScreen() {
    val viewModel: SearchViewModel = viewModel()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    // ⭐ THIS IS LaunchedEffect IN ACTION
    // When 'query' changes:
    //   1. The current LaunchedEffect block is CANCELLED
    //   2. A new one starts with the new query
    //   3. We delay 500ms — if query changes again, we restart
    //   4. If 500ms passes without changes, we run search()
    //
    // This is debouncing — wait until the user stops typing.
    LaunchedEffect(query) {
        delay(500)        // wait for typing to settle
        viewModel.search(query)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Search input
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                placeholder = { Text("Search fruits (debounced 500ms)...") },
                singleLine = true
            )

            // Results
            when (val state = searchState) {
                is UsersUiState.Loading -> LoadingView()
                is UsersUiState.Success -> {
                    if (state.users.isEmpty() && query.isNotBlank()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No results for \"$query\"",
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else if (state.users.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Type to search",
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(state.users) { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(10.dp)
                                ) {
                                    Text("• ${item.name}")
                                }
                            }
                        }
                    }
                }
                is UsersUiState.Error -> ErrorView(state.message) { }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 27: Counter ViewModel — Surviving Rotation
// ═══════════════════════════════════════════════════════════
//
// Compare to Level 5's "remember problem" demo.
// This counter SURVIVES rotation because the state lives in
// the ViewModel, not in remember.
//
// Try it: tap +, then rotate your phone — the count is still there!
// ═══════════════════════════════════════════════════════════

class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() = _count.update { it + 1 }
    fun decrement() = _count.update { it - 1 }
    fun reset() = _count.update { 0 }
}

@Composable
fun RotationProofCounter() {
    val viewModel: CounterViewModel = viewModel()
    val count by viewModel.count.collectAsStateWithLifecycle()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Rotation-Proof Counter",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                "$count",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.decrement() }) { Text("-") }
                OutlinedButton(onClick = { viewModel.reset() }) { Text("Reset") }
                Button(onClick = { viewModel.increment() }) { Text("+") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Rotate your phone — count survives!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN
// ═══════════════════════════════════════════════════════════

@Composable
fun Level7Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        L7SectionTitle("Topic 22-24: ViewModel + UI State Pattern")
        L7Intro(
            "This screen fetches users from a fake API. The API randomly " +
            "fails ~25% of the time so you can see ALL three states: " +
            "Loading, Success, and Error. Tap Refresh to try again."
        )
        UsersScreen()

        L7Divider()

        L7SectionTitle("Topic 25-26: LaunchedEffect + Debounced Search")
        L7Intro(
            "Type to search. Notice it doesn't fetch on every keystroke — " +
            "it waits 500ms after you stop typing. This is called debouncing. " +
            "LaunchedEffect cancels and restarts when its key (query) changes."
        )
        DebouncedSearchScreen()

        L7Divider()

        L7SectionTitle("Topic 27: Surviving Rotation")
        L7Intro(
            "ViewModel lives outside the composable, so its state survives " +
            "screen rotation, navigation back/forward, and recomposition."
        )
        RotationProofCounter()

        Spacer(modifier = Modifier.height(32.dp))
    }
}


// ── Helpers ──

@Composable
private fun L7SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun L7Intro(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun L7Divider() {
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

@Preview(showBackground = true, name = "Level 7 - Full")
@Composable
fun Level7ScreenPreview() {
    GoogleDesignTheme {
        Level7Screen()
    }
}
