package com.ankit.appui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankit.appui.ui.insights.DetailScreen
import com.ankit.appui.ui.insights.InsightsScreen
import com.ankit.appui.ui.insights.ProfileScreen
import com.ankit.appui.ui.insights.SettingsScreen
import com.ankit.appui.ui.theme.AppUITheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.MaterialSharedAxis
import com.google.accompanist.navigation.material.composable as materialComposable
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
    @OptIn(ExperimentalMaterialNavigationApi::class)
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
                // Set status bar and navigation bar to be transparent
                systemUiController.setSystemBarsColor(
                    color = androidx.compose.ui.graphics.Color.Transparent,
                    darkIcons = false
                )
                
                NavHost(
                    navController = navController,
                    startDestination = Screen.Insights.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(
                        route = Screen.Insights.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        InsightsScreen(navController)
                    }
                    
                    materialComposable(
                        route = Screen.Detail.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )
                        }
                    ) { backStackEntry ->
                        val cardId = backStackEntry.arguments?.getString("cardId") ?: "1"
                        DetailScreen(navController, cardId)
                    }
                    
                    composable(
                        route = Screen.Profile.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        ProfileScreen(navController)
                    }
                    
                    composable(
                        route = Screen.Settings.route,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )
                        }
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