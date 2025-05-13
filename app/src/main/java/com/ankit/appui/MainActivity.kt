package com.ankit.appui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.ankit.appui.ui.insights.InsightsScreen
import com.ankit.appui.ui.theme.AppUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window to handle edge-to-edge content
        enableEdgeToEdge()
        
        // Make the system bars (status and navigation) draw over the app
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            // Set our InsightsScreen as the main content
            InsightsScreen()
        }
    }
}

// Keeping these composables for reference
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppUITheme {
        Greeting("Android")
    }
}