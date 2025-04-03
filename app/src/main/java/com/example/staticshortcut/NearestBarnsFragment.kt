package com.example.staticshortcut

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define a constant tab width for consistency.
private val TAB_WIDTH = 80.dp

@Composable
fun NearestBarnsBottomSheetContent(onTabSelected: (Int) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Title
        Text(
            text = "Nearest Barns",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Full-width Tab Row (pill background + red selector)
        FullWidthTabRow(
            tabCount = 3,
            selectedTab = selectedTab,
            onTabSelected = { index ->
                selectedTab = index
                onTabSelected(index)
                val message = when (index) {
                    0 -> "List View"
                    1 -> "Map View"
                    else -> "More"
                }
                showToast(context, message)
            },
            // Provide the icons you want for each tab
            icons = listOf(
                android.R.drawable.btn_star_big_on,  // Tab 0
                android.R.drawable.ic_menu_save,     // Tab 1
                android.R.drawable.ic_menu_more      // Tab 2
            )
        )

        // Content based on selected tab
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTab) {
            0 -> BarnList()
            1 -> Text("Map View Content", modifier = Modifier.padding(16.dp))
            2 -> Text("More Content", modifier = Modifier.padding(16.dp))
        }
    }
}

/**
 * A composable that:
 * 1) Measures its own width using BoxWithConstraints.
 * 2) Draws a pill-shaped gray background across the entire width.
 * 3) Positions a red selector behind the currently selected tab.
 * 4) Renders tab icons that can be clicked to change selection.
 */
@Composable
fun FullWidthTabRow(
    tabCount: Int,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    icons: List<Int>
) {
    // Use BoxWithConstraints to get the container's width in pixels
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp) // Optional horizontal padding if desired
    ) {
        // This is the total width of the Box in pixels
        val containerWidthPx = constraints.maxWidth.toFloat()
        // The width of each tab in pixels
        val tabWidthPx = containerWidthPx / tabCount

        // Animate the offset of the red selector in pixels
        val targetOffsetPx = tabWidthPx * selectedTab
        val animatedOffsetPx by animateFloatAsState(
            targetValue = targetOffsetPx,
            label = "Tab Offset"
        )

        // Convert pixel values to Dp for layout
        val animatedOffsetDp = with(LocalDensity.current) { animatedOffsetPx.toDp() }
        val tabWidthDp = with(LocalDensity.current) { tabWidthPx.toDp() }

        // Gray pill background that fills the entire Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE0E0E0))
        )

        // Red selector behind the icons
        Box(
            modifier = Modifier
                .offset(x = animatedOffsetDp)
                .width(tabWidthDp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF8B0000))
        )

        // Row of icons on top
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Render each tab icon
            icons.forEachIndexed { index, iconRes ->
                val isSelected = index == selectedTab
                TabItem(
                    iconRes = iconRes,
                    isSelected = isSelected,
                    onClick = { onTabSelected(index) }
                )
            }
        }
    }
}

@Composable
fun TabItem(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    // Each item is evenly spaced in the Row, so no fixed width needed.
    // Just make it clickable and center the icon.
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f) // Make each tab a square in the horizontal arrangement
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun BarnList() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Rock Creek Farm at Chateau DuVall",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "5225 Glade Creek Road, Roanoke, VA 24012, United States",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Windy Mane Ranch",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "14265 Chaparral Lane, Roanoke, TX 76262, United States",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

// Simple toast helper
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun PreviewNearestBarnsBottomSheetContent() {
    NearestBarnsBottomSheetContent(onTabSelected = {})
}