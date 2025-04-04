package com.example.staticshortcut

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.staticshortcut.screen.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the data from the shortcut intent.
        // The shortcuts XML uses android:data like "app://nearest_barns" or "app://home".
        val shortcutData = intent?.data?.toString()

        // Pass the shortcutData to your Compose UI.
        setContent {
            MainScreen(shortcutData = shortcutData)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // If the app is already running and a new shortcut is pressed,
        // you could update your UI here by calling setContent or using a state holder.
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(shortcutData: String? = null) {
    val navController = rememberNavController()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Check the shortcut data once on composition start.
    LaunchedEffect(shortcutData) {
        // If the shortcut data indicates nearest barns, open the bottom sheet.
        if (shortcutData == "app://nearest_barns") {
            showBottomSheet = true
        }
        // If the shortcut is "home" or null, do nothing special
        // since the Home Screen is already the default.
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Button(onClick = { showBottomSheet = true }) {
                Text("Show Bottom Sheet")
            }

            // Navigation Host (Home Screen)
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen() // See next step for a simple home screen
                }
            }
        }

        // Show bottom sheet using ModalBottomSheet
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                modifier = Modifier.fillMaxHeight(0.5f) // Adjust height as needed
            ) {
                NearestBarnsBottomSheetContent(
                    onTabSelected = { tabIndex ->
                        // Handle tab selection if needed.
                    }
                )
            }
        }
    }
}


