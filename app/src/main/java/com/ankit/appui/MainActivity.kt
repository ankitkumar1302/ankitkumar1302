package com.ankit.appui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ankit.appui.ui.insights.DetailScreen
import com.ankit.appui.ui.insights.InsightsScreen
import com.ankit.appui.ui.insights.ProfileScreen
import com.ankit.appui.ui.insights.SettingsScreen
import com.ankit.appui.ui.theme.AlmostBlack
import com.ankit.appui.ui.theme.AppUITheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// Define app navigation routes
sealed class Screen(val route: String) {
    object Insights : Screen("insights")
    object Detail : Screen("detail/{cardId}")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                route.replace("{$arg}", arg)
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window to handle edge-to-edge content
        enableEdgeToEdge()
        
        // Make the system bars (status and navigation) draw over the app
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            
            AppUITheme {
                // Set status bar transparent and navigation bar to match bottom bar color
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = androidx.compose.ui.graphics.Color.Transparent,
                        darkIcons = false
                    )
                    
                    // Set navigation bar to match bottom bar
                    systemUiController.setNavigationBarColor(
                        color = AlmostBlack,
                        darkIcons = false
                    )
                }
                
                // Define standard animation specs for consistency
                val enterTransitionSpec = tween<Float>(200, easing = FastOutSlowInEasing)
                val exitTransitionSpec = tween<Float>(200, easing = FastOutSlowInEasing)
                
                NavHost(
                    navController = navController,
                    startDestination = Screen.Insights.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Insights screen - main dashboard
                    composable(
                        route = Screen.Insights.route,
                        enterTransition = { fadeIn(animationSpec = enterTransitionSpec) },
                        exitTransition = { fadeOut(animationSpec = exitTransitionSpec) }
                    ) {
                        InsightsScreen(navController)
                    }
                    
                    // Detail screen
                    composable(
                        route = Screen.Detail.route,
                        arguments = listOf(navArgument("cardId") { type = NavType.StringType }),
                        enterTransition = { fadeIn(animationSpec = enterTransitionSpec) },
                        exitTransition = { fadeOut(animationSpec = exitTransitionSpec) }
                    ) { backStackEntry ->
                        val cardId = backStackEntry.arguments?.getString("cardId") ?: "default"
                        DetailScreen(navController, cardId)
                    }
                    
                    // Profile screen
                    composable(
                        route = Screen.Profile.route,
                        enterTransition = { fadeIn(animationSpec = enterTransitionSpec) },
                        exitTransition = { fadeOut(animationSpec = exitTransitionSpec) }
                    ) {
                        ProfileScreen(navController)
                    }
                    
                    // Settings screen
                    composable(
                        route = Screen.Settings.route,
                        enterTransition = { fadeIn(animationSpec = enterTransitionSpec) },
                        exitTransition = { fadeOut(animationSpec = exitTransitionSpec) }
                    ) {
                        SettingsScreen(navController)
                    }
                }
            }
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