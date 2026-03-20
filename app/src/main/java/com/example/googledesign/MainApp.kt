package com.example.googledesign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.googledesign.learn.Level1Screen
import com.example.googledesign.learn.Level2Screen
import com.example.googledesign.learn.Level3Screen
import com.example.googledesign.learn.Level4Screen
import com.example.googledesign.learn.Level5Screen
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ═══════════════════════════════════════════════════════════
// DATA
// ═══════════════════════════════════════════════════════════

data class LevelInfo(
    val number: Int,
    val title: String,
    val subtitle: String,
    val topics: List<String>
)

val levels = listOf(
    LevelInfo(1, "The Basics", "Composable, Text, Button, Layouts", listOf("@Composable", "Text & Button", "Modifier", "Column, Row, Box")),
    LevelInfo(2, "Styling", "Colors, shapes, typography, spacing", listOf("Colors & Shapes", "Typography", "Padding vs Spacing")),
    LevelInfo(3, "Interactivity", "State, input fields, user actions", listOf("State", "TextField", "Clicks & Toggles")),
    LevelInfo(4, "Lists & Nav", "Scrollable lists, cards, screens", listOf("LazyColumn", "Cards & ListItem", "Navigation")),
    LevelInfo(5, "App Patterns", "Scaffold, ViewModel, recomposition", listOf("Full Screens", "ViewModel", "Recomposition")),
)


// ═══════════════════════════════════════════════════════════
// NAVIGATION
// ═══════════════════════════════════════════════════════════

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeLandingPage(
                onLevelClick = { navController.navigate("level/$it") }
            )
        }

        composable("level/{levelNumber}") { backStackEntry ->
            val levelNumber = backStackEntry.arguments
                ?.getString("levelNumber")
                ?.toIntOrNull() ?: 1

            LevelDetailScreen(
                levelNumber = levelNumber,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelDetailScreen(levelNumber: Int, onBack: () -> Unit) {
    val level = levels.getOrNull(levelNumber - 1) ?: levels[0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Level $levelNumber: ${level.title}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        when (levelNumber) {
            1 -> Level1Screen(modifier = Modifier.padding(innerPadding))
            2 -> Level2Screen(modifier = Modifier.padding(innerPadding))
            3 -> Level3Screen(modifier = Modifier.padding(innerPadding))
            4 -> Level4Screen(modifier = Modifier.padding(innerPadding))
            5 -> Level5Screen(modifier = Modifier.padding(innerPadding))
        }
    }
}


// ═══════════════════════════════════════════════════════════
// HOME LANDING PAGE
// ═══════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLandingPage(
    modifier: Modifier = Modifier,
    onLevelClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        // Fixed top section + scrollable cards below
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // ── FIXED SECTION (doesn't scroll) ──

            // Hero — compact for real phones
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    "Jetpack Compose",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Guide",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Learn Material 3 UI development from scratch. 16 topics across 5 levels.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Stats — compact
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("5", "Levels")
                StatItem("16", "Topics")
                StatItem("80+", "Examples")
            }

            // Sticky heading
            Text(
                "Learning Path",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
            )

            // ── SCROLLABLE SECTION (only this scrolls) ──

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(levels) { index, level ->
                    LevelCard(
                        level = level,
                        onClick = { onLevelClick(level.number) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            number,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}


// ═══════════════════════════════════════════════════════════
// LEVEL CARD — Clean, simple, consistent
// ═══════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelCard(level: LevelInfo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Level number badge — compact
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${level.number}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    level.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    level.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Topic chips — compact
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    level.topics.forEach { topic ->
                        Text(
                            text = topic,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Arrow
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


// ═══════════════════════════════════════════════════════════
// PREVIEWS
// ═══════════════════════════════════════════════════════════

@Preview(showBackground = true, name = "Home - Light")
@Composable
fun MainAppPreviewLight() {
    GoogleDesignTheme(darkTheme = false) {
        MainApp()
    }
}

@Preview(showBackground = true, name = "Home - Dark")
@Composable
fun MainAppPreviewDark() {
    GoogleDesignTheme(darkTheme = true) {
        MainApp()
    }
}
