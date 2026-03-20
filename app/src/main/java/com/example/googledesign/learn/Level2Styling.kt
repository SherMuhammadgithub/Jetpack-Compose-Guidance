package com.example.googledesign.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googledesign.ui.theme.GoogleDesignTheme

// ╔════════════════════════════════════════════════════════════╗
// ║            LEVEL 2: STYLING & APPEARANCE                  ║
// ║                                                           ║
// ║  Topic 5: Colors & Shapes                                ║
// ║  Topic 6: Typography & Text Styling                      ║
// ║  Topic 7: Padding vs Spacing                             ║
// ╚════════════════════════════════════════════════════════════╝


// ═══════════════════════════════════════════════════════════
// TOPIC 5: Colors & Shapes
// ═══════════════════════════════════════════════════════════
//
// COLORS — There are 3 ways to use colors in Compose:
//
//   1. Hardcoded:  Color.Red, Color.Blue, Color(0xFF1A73E8)
//      → Quick but BAD for real apps (won't change with dark mode)
//
//   2. Theme colors: MaterialTheme.colorScheme.primary
//      → BEST for real apps (auto-switches light/dark)
//
//   3. Gradient: Brush.horizontalGradient(listOf(Color1, Color2))
//      → Fancy! Blends two or more colors together
//
// SHAPES — Control the corners/edges of composables:
//
//   RoundedCornerShape(12.dp)  → Rounded corners
//   CircleShape                → Perfect circle
//   CutCornerShape(12.dp)     → Corners are cut diagonally
//   RectangleShape             → Sharp 90° corners (default)
//
// ═══════════════════════════════════════════════════════════


// --- 5A: Ways to Define Colors ---

@Composable
fun ColorWays() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        // WAY 1: Named colors (built-in)
        // Compose has these ready: Red, Blue, Green, Yellow, Cyan,
        // Magenta, Black, White, Gray, LightGray, DarkGray, Transparent
        Text(
            "Named: Color.Blue",
            color = Color.Blue
        )

        // WAY 2: Hex color — Color(0xAARRGGBB)
        //   AA = Alpha (transparency). FF = fully visible, 00 = invisible
        //   RR = Red, GG = Green, BB = Blue
        //
        //   Example: 0xFF1A73E8
        //            ││  └────── 1A73E8 = Google Blue
        //            └┘
        //            FF = 100% opaque (no transparency)
        Text(
            "Hex: Color(0xFF1A73E8)",
            color = Color(0xFF1A73E8)
        )

        // WAY 3: RGBA — Color(red, green, blue, alpha) each 0f to 1f
        Text(
            "RGBA: Color(1f, 0f, 0f) = Red",
            color = Color(red = 1f, green = 0f, blue = 0f)
        )

        // WAY 4: Semi-transparent (alpha < FF)
        //   0x80 = 50% transparent (80 in hex = 128 out of 255)
        Text(
            "50% transparent blue",
            color = Color(0x801A73E8),
            style = MaterialTheme.typography.bodyLarge
        )

        // WAY 5: Copy a color and change just one property
        // .copy() is super useful — take an existing color, tweak it
        Text(
            "Primary but 50% transparent",
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )

        // WAY 6: Theme colors — THE BEST WAY for real apps
        // These automatically change between light/dark mode
        Text(
            "Theme: MaterialTheme.colorScheme.primary",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


// --- 5B: Backgrounds & Gradients ---

@Composable
fun BackgroundExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        // Solid color background
        Text(
            "Solid background",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        // GRADIENT background
        // A gradient smoothly blends from one color to another
        //
        //   horizontalGradient: Left → Right
        //   verticalGradient:   Top → Bottom
        //   linearGradient:     Any angle
        Text(
            "Horizontal gradient",
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1A73E8),   // Starts with Blue
                            Color(0xFF188038)     // Ends with Green
                        )
                    )
                )
                .padding(12.dp),
            color = Color.White
        )

        Text(
            "Vertical gradient",
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFD93025),    // Starts with Red (top)
                            Color(0xFFF9AB00)     // Ends with Yellow (bottom)
                        )
                    )
                )
                .padding(12.dp),
            color = Color.White
        )

        // Multi-color gradient (3+ colors)
        Text(
            "Rainbow gradient!",
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFD93025),   // Red
                            Color(0xFFF9AB00),   // Yellow
                            Color(0xFF188038),   // Green
                            Color(0xFF1A73E8),   // Blue
                        )
                    )
                )
                .padding(12.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}


// --- 5C: Shapes —  Controlling Corners ---
//
// Shapes control how the CORNERS of a composable look.
//
// Visual guide:
//
//   RoundedCornerShape:     CircleShape:        CutCornerShape:
//   ╭──────────────╮       ╭────────╮          /──────────\
//   │              │       │        │         /            \
//   │              │       │        │         \            /
//   ╰──────────────╯       ╰────────╯          \──────────/
//
// You can apply shapes via:
//   .background(color, shape)   → colored background with shape
//   .clip(shape)                → clips/crops content to shape
//   .border(width, color, shape) → draws a border with shape

@Composable
fun ShapeExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // --- RoundedCornerShape ---
        // All corners same radius
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)    // All 4 corners = 12dp
                )
                .padding(16.dp)
        ) {
            Text(
                "RoundedCornerShape(12.dp)",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Different radius for each corner!
        // Order: topStart, topEnd, bottomEnd, bottomStart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        topStart = 24.dp,      // Big round top-left
                        topEnd = 0.dp,          // Sharp top-right
                        bottomEnd = 24.dp,      // Big round bottom-right
                        bottomStart = 0.dp      // Sharp bottom-left
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                "Mixed corners: 24, 0, 24, 0",
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Using PERCENTAGE for rounded corners
        // 50% = fully rounded on short side = pill shape!
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(50)    // 50% = pill shape
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                "Pill shape (50%)",
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        // --- CircleShape ---
        // Makes a perfect circle (use with equal width & height!)
        Box(
            modifier = Modifier
                .size(64.dp)                              // Must be square!
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Hi", color = MaterialTheme.colorScheme.onPrimary)
        }

        // --- CutCornerShape ---
        // Corners are cut diagonally (like a diamond edge)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CutCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                "CutCornerShape(12.dp)",
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}


// --- 5D: Borders & Shadows ---
//
// .border() → draws an outline around a composable
// .shadow() → adds a drop shadow (elevation effect)

@Composable
fun BorderAndShadowExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // Simple border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text("2dp border, rounded")
        }

        // Gradient border (fancy!)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1A73E8),
                            Color(0xFF188038)
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Text("Gradient border!")
        }

        // Shadow (elevation)
        // shadow() must come BEFORE background() in the modifier chain
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,               // How "high" above surface
                    shape = RoundedCornerShape(12.dp)
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text("8dp shadow elevation")
        }

        // CARD — Material 3's built-in component with shadow + shape
        // Card is basically: Box + shape + shadow + background all in one!
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                "I'm a Card! Shape + Shadow built-in",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


// --- 5E: clip() — Cropping Content to a Shape ---
//
// .clip(shape) crops EVERYTHING inside to that shape.
// Useful for: profile pictures, rounded image corners
//
// Difference between .background(shape) vs .clip(shape):
//   .background(color, shape) → only the BACKGROUND follows the shape
//   .clip(shape) → EVERYTHING inside (text, images, children) gets cropped

@Composable
fun ClipExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Without clip — background is rounded but text can overflow
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    Color(0xFFD3E3FD),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("No clip", fontSize = 11.sp)
        }

        // With clip — everything is cropped to the circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)                        // Crops everything!
                .background(Color(0xFFCEEAD6)),
            contentAlignment = Alignment.Center
        ) {
            Text("Clipped!", fontSize = 11.sp)
        }

        // clip is ESSENTIAL for images:
        // Image(
        //     painter = painterResource(R.drawable.photo),
        //     modifier = Modifier
        //         .size(80.dp)
        //         .clip(CircleShape)    // Makes photo circular!
        // )
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 6: Typography & Text Styling
// ═══════════════════════════════════════════════════════════
//
// We already set up the TYPE SCALE in Type.kt (Level 1).
// Now let's learn how to STYLE text beyond just font size.
//
// Key concepts:
//   - TextStyle → a bundle of: font, size, weight, color, etc.
//   - MaterialTheme.typography → your pre-defined styles
//   - buildAnnotatedString → MULTIPLE styles in ONE text
//   - TextAlign → left, center, right, justify
//   - TextOverflow → what happens when text is too long
//   - TextDecoration → underline, line-through
// ═══════════════════════════════════════════════════════════


// --- 6A: Font Weight & Style ---

@Composable
fun FontWeightExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        // FontWeight controls how THICK the letters are
        // Range: Thin (100) → Black (900)
        //
        //   Thin        W100  ─── thinnest
        //   ExtraLight  W200
        //   Light       W300
        //   Normal      W400  ─── default
        //   Medium      W500
        //   SemiBold    W600
        //   Bold        W700  ─── bold
        //   ExtraBold   W800
        //   Black       W900  ─── thickest

        Text("Thin (W100)", fontWeight = FontWeight.Thin)
        Text("Light (W300)", fontWeight = FontWeight.Light)
        Text("Normal (W400)", fontWeight = FontWeight.Normal)
        Text("Medium (W500)", fontWeight = FontWeight.Medium)
        Text("Bold (W700)", fontWeight = FontWeight.Bold)
        Text("Black (W900)", fontWeight = FontWeight.Black)

        Spacer(modifier = Modifier.height(8.dp))

        // FontStyle: Normal or Italic
        Text("Normal style", fontStyle = FontStyle.Normal)
        Text("Italic style", fontStyle = FontStyle.Italic)
    }
}


// --- 6B: Text Alignment ---

@Composable
fun TextAlignExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // textAlign only matters when the Text is WIDER than its content
        // So we use fillMaxWidth() to make it full width

        Text(
            "Left aligned (default)",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(8.dp),
            textAlign = TextAlign.Start      // Left (or Right in RTL languages)
        )

        Text(
            "Center aligned",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            "Right aligned",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(8.dp),
            textAlign = TextAlign.End        // Right (or Left in RTL)
        )
    }
}


// --- 6C: Text Decoration (Underline, Strikethrough) ---

@Composable
fun TextDecorationExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        Text(
            "Underlined text",
            textDecoration = TextDecoration.Underline
        )

        Text(
            "Strikethrough text",
            textDecoration = TextDecoration.LineThrough
        )

        // Combine both!
        Text(
            "Both underline + strikethrough",
            textDecoration = TextDecoration.Underline + TextDecoration.LineThrough
        )
    }
}


// --- 6D: Text Overflow — What happens when text is too long? ---

@Composable
fun TextOverflowExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        val longText = "This is a very long text that will not fit in one line and we need to handle it properly."

        // maxLines = 1 forces single line. But what about extra text?

        // Clip — just cuts off the text
        Text(
            longText,
            maxLines = 1,
            overflow = TextOverflow.Clip,           // Just chops it off
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(8.dp)
        )

        // Ellipsis — adds "..." at the end (MOST COMMON)
        Text(
            longText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,       // Adds "..." at the end
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD3E3FD))
                .padding(8.dp)
        )

        // maxLines = 2 → allow 2 lines, then ellipsis
        Text(
            longText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCEEAD6))
                .padding(8.dp)
        )
    }
}


// --- 6E: Multiple Styles in ONE Text (AnnotatedString) ---
//
// What if you want "Hello World" where "Hello" is blue and "World" is red?
// You can't do that with a single Text() and single style.
//
// Solution: buildAnnotatedString + withStyle
//
// Think of it like HTML:
//   <span style="color:blue">Hello </span><span style="color:red">World</span>
//
// In Compose:
//   buildAnnotatedString {
//       withStyle(SpanStyle(color = Blue)) { append("Hello ") }
//       withStyle(SpanStyle(color = Red)) { append("World") }
//   }

@Composable
fun AnnotatedStringExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        // Example 1: Different colors in one text
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF1A73E8))) {
                    append("Blue ")
                }
                withStyle(SpanStyle(color = Color(0xFFD93025))) {
                    append("Red ")
                }
                withStyle(SpanStyle(color = Color(0xFF188038))) {
                    append("Green")
                }
            },
            fontSize = 18.sp
        )

        // Example 2: Mixed bold and normal
        Text(
            buildAnnotatedString {
                append("This is ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("bold")
                }
                append(" and this is ")
                withStyle(SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )) {
                    append("bold + colored")
                }
            }
        )

        // Example 3: Multiple properties at once
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )) {
                    append("Big Title ")
                }
                withStyle(SpanStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline,
                    fontStyle = FontStyle.Italic
                )) {
                    append("(small subtitle)")
                }
            }
        )

        // Example 4: Underline just ONE word
        Text(
            buildAnnotatedString {
                append("Click ")
                withStyle(SpanStyle(
                    color = Color(0xFF1A73E8),
                    textDecoration = TextDecoration.Underline
                )) {
                    append("here")
                }
                append(" to learn more.")
            }
        )
    }
}


// --- 6F: Different Font Families ---

@Composable
fun FontFamilyExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        // Built-in font families (no need to download anything)

        Text("Default (Roboto)", fontFamily = FontFamily.Default)
        Text("Serif (like Times New Roman)", fontFamily = FontFamily.Serif)
        Text("SansSerif", fontFamily = FontFamily.SansSerif)
        Text("Monospace (like code)", fontFamily = FontFamily.Monospace)
        Text("Cursive", fontFamily = FontFamily.Cursive)

        // For CUSTOM fonts (Google Fonts, downloaded .ttf files),
        // see the commented section in Type.kt
    }
}


// ═══════════════════════════════════════════════════════════
// TOPIC 7: Padding vs Spacing — The 3 Ways to Add Space
// ═══════════════════════════════════════════════════════════
//
// This confuses EVERYONE at first. There are 3 ways to add space:
//
//   1. padding()      → space INSIDE a composable's border
//   2. Spacer()       → an invisible composable that takes up space
//   3. Arrangement    → controls gaps between children in Column/Row
//
// Visual comparison:
//
//   PADDING (inside the border):
//   ┌──────────────────┐
//   │    (padding)      │
//   │  ┌────────────┐  │
//   │  │  Content   │  │
//   │  └────────────┘  │
//   │    (padding)      │
//   └──────────────────┘
//
//   SPACER (between two items):
//   ┌──────────┐
//   │ Item A   │
//   └──────────┘
//     (Spacer)     ← empty invisible box
//   ┌──────────┐
//   │ Item B   │
//   └──────────┘
//
//   ARRANGEMENT (auto-gaps in Column/Row):
//   ┌──────────┐
//   │ Item A   │
//   │  (gap)   │  ← automatic spacing
//   │ Item B   │
//   │  (gap)   │  ← automatic spacing
//   │ Item C   │
//   └──────────┘
// ═══════════════════════════════════════════════════════════


// --- 7A: Padding Deep Dive ---

@Composable
fun PaddingDeepDive() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // All sides equal
        Text(
            "padding(16.dp) = all sides",
            modifier = Modifier
                .background(Color(0xFFD3E3FD))
                .padding(16.dp)       // 16dp on ALL four sides
        )

        // Horizontal & Vertical separately
        Text(
            "padding(horizontal=24, vertical=8)",
            modifier = Modifier
                .background(Color(0xFFCEEAD6))
                .padding(
                    horizontal = 24.dp,   // Left + Right = 24dp each
                    vertical = 8.dp       // Top + Bottom = 8dp each
                )
        )

        // Each side different
        Text(
            "All sides different",
            modifier = Modifier
                .background(Color(0xFFFCE8E6))
                .padding(
                    start = 32.dp,    // Left (32dp — big indent)
                    top = 4.dp,       // Top (small)
                    end = 8.dp,       // Right (medium)
                    bottom = 16.dp    // Bottom (large)
                )
        )

        // IMPORTANT: padding vs margin
        // In XML/CSS: padding = inside, margin = outside
        // In Compose: there is NO "margin"! You use padding TWICE.
        //
        // "Margin" effect:
        //   Modifier
        //     .padding(16.dp)       ← acts like MARGIN (space outside)
        //     .background(Blue)     ← background starts here
        //     .padding(12.dp)       ← acts like PADDING (space inside)
        //
        // Visual:
        //   ┌────────────────────────────┐
        //   │  16dp "margin" (no bg)     │
        //   │  ┌──────────────────────┐  │
        //   │  │▓▓ 12dp padding ▓▓▓▓▓│  │
        //   │  │▓                    ▓│  │
        //   │  │▓    Content         ▓│  │
        //   │  │▓                    ▓│  │
        //   │  │▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓│  │
        //   │  └──────────────────────┘  │
        //   └────────────────────────────┘

        Text(
            "Fake margin + real padding",
            modifier = Modifier
                .padding(16.dp)                              // "Margin" (outer space)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp)                              // Padding (inner space)
        )
    }
}


// --- 7B: Spacer — The Invisible Gap ---

@Composable
fun SpacerExamples() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F3F4))
            .padding(16.dp)
    ) {
        Text("Item A", style = MaterialTheme.typography.bodyLarge)

        // Spacer = an invisible empty box
        // In a Column, give it height
        // In a Row, give it width
        Spacer(modifier = Modifier.height(24.dp))    // 24dp vertical gap

        Text("Item B (24dp below A)", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))     // 8dp vertical gap

        Text("Item C (8dp below B)", style = MaterialTheme.typography.bodyLarge)

        // TRICK: Spacer with weight pushes things apart!
        // weight(1f) = "take ALL remaining space"
        Spacer(modifier = Modifier.weight(1f))

        Text(
            "I'm pushed to the bottom!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// Spacer with weight in a ROW — very useful!
@Composable
fun SpacerWeightInRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD3E3FD))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Title", fontWeight = FontWeight.Bold)

        // This spacer PUSHES the button to the right edge
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { }) {
            Text("Action")
        }
    }
    // Result:
    // |Title                           [Action]|
    // ↑ Spacer fills all middle space, pushing them apart
}


// --- 7C: Arrangement — Auto-Spacing in Column/Row ---

@Composable
fun ArrangementExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            "spacedBy(12.dp) — equal gaps",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // spacedBy = automatic equal gaps between ALL children
        // This is the CLEANEST way to space items!
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)  // 12dp between each
        ) {
            Text("Item 1", modifier = Modifier.background(Color(0xFFD3E3FD)).padding(8.dp))
            Text("Item 2", modifier = Modifier.background(Color(0xFFD3E3FD)).padding(8.dp))
            Text("Item 3", modifier = Modifier.background(Color(0xFFD3E3FD)).padding(8.dp))
        }

        // Same in a Row
        Text(
            "Row with spacedBy(8.dp)",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F3F4))
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("A", modifier = Modifier.background(Color(0xFFCEEAD6)).padding(8.dp))
            Text("B", modifier = Modifier.background(Color(0xFFCEEAD6)).padding(8.dp))
            Text("C", modifier = Modifier.background(Color(0xFFCEEAD6)).padding(8.dp))
        }
    }
}


// --- 7D: When to Use Which? ---
//
// ┌────────────────────────┬───────────────────────────────────┐
// │  Situation             │  Use                              │
// ├────────────────────────┼───────────────────────────────────┤
// │  Space inside a box    │  .padding()                       │
// │  Space around a box    │  .padding() before .background()  │
// │  Gap between 2 items   │  Spacer()                         │
// │  Equal gaps in a list  │  Arrangement.spacedBy()           │
// │  Push item to edge     │  Spacer(Modifier.weight(1f))     │
// └────────────────────────┴───────────────────────────────────┘

@Composable
fun WhenToUseWhat() {
    // A practical example combining all 3 spacing methods
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),                  // ① padding as "margin" outside card
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),   // ② padding inside card
            verticalArrangement = Arrangement.spacedBy(8.dp)  // ③ arrangement for equal gaps
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Notification",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))  // ④ spacer pushes time to right
                Text(
                    "2m ago",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Text(
                "You have a new message from Alex.",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)  // ⑤ arrangement for button gaps
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Reply")
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text("Dismiss")
                }
            }
        }
    }
}


// ═══════════════════════════════════════════════════════════
// FULL DEMO SCREEN — Shows all Level 2 examples
// ═══════════════════════════════════════════════════════════

@Composable
fun Level2Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── TOPIC 5: Colors & Shapes ──
        SectionHeader("Topic 5A: Ways to Use Colors")
        ColorWays()

        Divider()

        SectionHeader("Topic 5B: Backgrounds & Gradients")
        BackgroundExamples()

        Divider()

        SectionHeader("Topic 5C: Shapes (Corners)")
        ShapeExamples()

        Divider()

        SectionHeader("Topic 5D: Borders & Shadows")
        BorderAndShadowExamples()

        Divider()

        SectionHeader("Topic 5E: clip() — Cropping")
        ClipExample()

        Divider()

        // ── TOPIC 6: Typography ──
        SectionHeader("Topic 6A: Font Weights")
        FontWeightExamples()

        Divider()

        SectionHeader("Topic 6B: Text Alignment")
        TextAlignExamples()

        Divider()

        SectionHeader("Topic 6C: Text Decoration")
        TextDecorationExamples()

        Divider()

        SectionHeader("Topic 6D: Text Overflow")
        TextOverflowExamples()

        Divider()

        SectionHeader("Topic 6E: Multiple Styles (AnnotatedString)")
        AnnotatedStringExamples()

        Divider()

        SectionHeader("Topic 6F: Font Families")
        FontFamilyExamples()

        Divider()

        // ── TOPIC 7: Padding vs Spacing ──
        SectionHeader("Topic 7A: Padding Deep Dive")
        PaddingDeepDive()

        Divider()

        SectionHeader("Topic 7B: Spacer")
        SpacerExamples()

        Spacer(modifier = Modifier.height(8.dp))
        SpacerWeightInRow()

        Divider()

        SectionHeader("Topic 7C: Arrangement")
        ArrangementExamples()

        Divider()

        SectionHeader("Topic 7D: Practical Example")
        WhenToUseWhat()

        Spacer(modifier = Modifier.height(32.dp))
    }
}


// ── Helper composables used above ──

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun Divider() {
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

@Preview(showBackground = true, name = "Level 2 - Full Screen")
@Composable
fun Level2ScreenPreview() {
    GoogleDesignTheme {
        Level2Screen()
    }
}

@Preview(showBackground = true, name = "Notification Card")
@Composable
fun NotificationCardPreview() {
    GoogleDesignTheme {
        WhenToUseWhat()
    }
}
