package com.example.staticshortcut

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    // Total width for the 3 tabs: 3 * TAB_WIDTH = 240.dp
    val totalWidth = TAB_WIDTH * 3

    // Animate the red selector’s offset based on the selected tab.
    val selectorOffsetX by animateDpAsState(
        targetValue = when (selectedTab) {
            0 -> 0.dp
            1 -> TAB_WIDTH
            2 -> TAB_WIDTH * 2
            else -> 0.dp
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Title with padding
        Text(
            text = "Nearest Barns",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Center the tab container horizontally
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Fixed-width container for the pill background, selector, and icons
            Box(
                modifier = Modifier
                    .width(totalWidth)
                    .height(48.dp)
            ) {
                // Gray pill background that spans the full container
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(24.dp)
                        )
                )
                // Red selector that moves under each tab (drawn first so it appears behind the icons)
                Box(
                    modifier = Modifier
                        .offset(x = selectorOffsetX)
                        .width(TAB_WIDTH)
                        .fillMaxHeight()
                        .background(
                            color = Color(0xFF8B0000),
                            shape = RoundedCornerShape(24.dp)
                        )
                )
                // Row of tab icons
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TabItem(
                        iconRes = android.R.drawable.btn_star_big_on,
                        isSelected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            onTabSelected(0)
                            showToast(context, "List View")
                        }
                    )
                    TabItem(
                        iconRes = android.R.drawable.ic_menu_save,
                        isSelected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            onTabSelected(1)
                            showToast(context, "Map View")
                        }
                    )
                    TabItem(
                        iconRes = android.R.drawable.ic_menu_more,
                        isSelected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            onTabSelected(2)
                            showToast(context, "More")
                        }
                    )
                }
            }
        }

        // Content based on selected tab
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTab) {
            0 -> BarnList()
            1 -> Text("Map View Content", modifier = Modifier.padding(16.dp))
            2 -> Text("More Content", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun TabItem(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    // Each TabItem occupies a fixed area (80.dp x 48.dp).
    Box(
        modifier = Modifier
            .size(TAB_WIDTH, 48.dp)
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

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun PreviewNearestBarnsBottomSheetContent() {
    NearestBarnsBottomSheetContent(onTabSelected = {})
}