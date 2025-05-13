package com.ankit.appui.ui.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.appui.R // For placeholder icons, replace with actual resources
import com.ankit.appui.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(onProfileClick: () -> Unit = {}) {
    Surface(
        color = AlmostBlack,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp), // Specific padding values
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon/avatar with improved ripple effect
            val interactionSource = remember { MutableInteractionSource() }
            
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(TwitchPurple.copy(alpha = 0.7f))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(
                            bounded = true,
                            color = OffWhite
                        ),
                        onClick = onProfileClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Twitch title and dropdown
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* Handle profile selection */ }
            ) {
                Text(
                    text = "twitch",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OffWhite,
                        fontSize = 20.sp
                    )
                )
                
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Select Profile",
                    tint = OffWhite,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Action buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Counter badge
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkGray)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "14",
                        style = TextStyle(
                            color = OffWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Notification icon
                IconButton(
                    onClick = { /* Handle notification click */ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = OffWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InsightsTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Overview", "Insights", "Direct", "Conversations", "Con...")
    
    // Surface wrapper for the tab row
    Surface(
        color = AlmostBlack,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Custom tab row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    CustomTab(
                        title = title,
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Divider at the bottom of tabs
            Divider(
                color = Color.DarkGray.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (selected) TwitchPurple else LightGray
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = title,
            style = TabTextStyle.copy(
                color = color,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        if (selected) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(100))
                    .background(TwitchPurple)
            )
        } else {
            Spacer(modifier = Modifier.height(9.dp))
        }
    }
}

@Composable
fun CustomBottomNavigation(selectedItemIndex: Int, onItemClick: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Create", Icons.Filled.Add, Icons.Filled.Add),
        BottomNavItem("Home", Icons.Filled.Home, Icons.Filled.Home),
        BottomNavItem("AI Hub", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome)
    )

    // Custom shape for bottom navigation with rounded top corners
    val navShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = navShape,
        color = AlmostBlack, // Fixed color to match the rest of the UI
        tonalElevation = 8.dp // Using tonalElevation for better Material 3 aesthetics
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Blur effect background (subtle)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(radius = 10.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                AlmostBlack.copy(alpha = 0.8f),
                                DarkGray.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
            
            // Navigation Items
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items.forEachIndexed { index, item ->
                    BottomNavItem(
                        item = item,
                        isSelected = index == selectedItemIndex,
                        onItemClick = { onItemClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    // Define colors based on selection
    val iconColor = if (isSelected) TwitchPurple else LightGray
    val textColor = if (isSelected) TwitchPurple else LightGray
    val backgroundColor = if (isSelected) {
        // Very subtle background for selected item
        TwitchPurple.copy(alpha = 0.1f)
    } else {
        Color.Transparent
    }
    
    // Modern ripple effect with custom indication
    val interactionSource = remember { MutableInteractionSource() }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                ),
                onClick = onItemClick
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}

data class BottomNavItem(val title: String, val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector, val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp) // Main horizontal padding for content area
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Space below TabRow

        // Top row: Geo and Demographic cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Gap between cards
        ) {
            Box(modifier = Modifier.weight(1f)) {
                GeoCard()
            }
            Box(modifier = Modifier.weight(1f)) {
                DemographicCard()
            }
        }
        
        // Second row: Audience activity and Followers online
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Gap between cards
        ) {
            Box(modifier = Modifier.weight(1f)) {
                AudienceActivityCard()
            }
            Box(modifier = Modifier.weight(1f)) {
                FollowersOnlineCard()
            }
        }

        // Engagement section
        EngagementSectionTitle()

        // Engagement metrics row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Consistent spacing
        ) {
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_lightning),
                title = "ER",
                value = "11.3%",
                changePercentage = "1.32%",
                modifier = Modifier.weight(1f)
            )
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_reach),
                title = "ER Reach",
                value = "7.2%",
                changePercentage = "0.3%",
                modifier = Modifier.weight(1f)
            )
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_views),
                title = "ER Views",
                value = "5.3",
                changePercentage = "0.2%",
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Insight Report banner
        InsightReportBanner()

        // Bottom spacing for scrolling
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen() {
    var selectedTabIndex by remember { mutableStateOf(1) } // "Insights" is selected
    var selectedBottomNavItem by remember { mutableStateOf(1) } // "Home" is selected

    AppUITheme {
        Scaffold(
            containerColor = AlmostBlack,
            contentWindowInsets = WindowInsets.statusBars,
            topBar = {
                Column(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    CustomTopAppBar()
                    InsightsTabRow(
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it }
                    )
                }
            },
            bottomBar = {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    CustomBottomNavigation(
                        selectedItemIndex = selectedBottomNavItem,
                        onItemClick = { selectedBottomNavItem = it }
                    )
                }
            }
        ) { paddingValues ->
            MainContent(Modifier.padding(paddingValues))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InsightsScreenPreview() {
    InsightsScreen()
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CustomTopAppBarPreview(){
    AppUITheme {
        CustomTopAppBar()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun InsightsTabRowPreview(){
    AppUITheme {
        var selectedTabIndex by remember { mutableStateOf(1) }
        InsightsTabRow(selectedTabIndex = selectedTabIndex, onTabSelected = {selectedTabIndex = it})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun CustomBottomNavigationPreview(){
    AppUITheme {
        var selectedItemIndex by remember { mutableStateOf(1) }
        CustomBottomNavigation(selectedItemIndex = selectedItemIndex, onItemClick = {selectedItemIndex = it})
    }
}

// Standard card dimensions for consistency
object CardDimensions {
    val cardWidth = 340.dp
    val cardHeight = 180.dp
    val smallCardHeight = 120.dp
    val standardElevation = 4.dp
    val standardCornerRadius = 16.dp
}

// Updated cards with better ripple effects and standardized dimensions

@Composable
fun DemographicsCard(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(CardDimensions.standardCornerRadius))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                ),
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = CardDimensions.standardElevation
        )
    ) {
        // ... existing content ...
    }
}

// Similar updates for other card components...

@Composable
fun InsightsScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(1) } // Default to Insights tab
    var selectedBottomNavIndex by remember { mutableStateOf(1) } // Default to Home
    
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = { CustomTopAppBar { navController.navigate(Screen.Profile.route) } },
        bottomBar = { 
            CustomBottomNavigation(
                selectedItemIndex = selectedBottomNavIndex,
                onItemClick = { selectedBottomNavIndex = it }
            )
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = AlmostBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            InsightsTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Main grid of insight cards with standardized dimensions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Standardized size demographics card with shared element transition
                    DemographicsCard(
                        modifier = Modifier
                            .width(CardDimensions.cardWidth / 2 - 8.dp)
                            .height(CardDimensions.cardHeight),
                        onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "demographics")) }
                    )
                    
                    // Standardized size geo card with shared element transition
                    GeoDistributionCard(
                        modifier = Modifier
                            .width(CardDimensions.cardWidth / 2 - 8.dp)
                            .height(CardDimensions.cardHeight),
                        onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "geo")) }
                    )
                }
                
                // Full width audience activity card with enhanced animation
                AudienceActivityCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(CardDimensions.cardHeight),
                    onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "audience")) }
                )
                
                // Engagement metrics with uniform sizing and elevation
                EngagementMetricsRow(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { metric -> navController.navigate(Screen.Detail.route.replace("{cardId}", metric)) }
                )
                
                // Followers online card with enhanced graph
                FollowersOnlineCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(CardDimensions.cardHeight),
                    onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "followers")) }
                )
            }
        }
    }
} 