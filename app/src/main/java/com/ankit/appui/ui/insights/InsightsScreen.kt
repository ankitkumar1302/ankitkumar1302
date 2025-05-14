package com.ankit.appui.ui.insights

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ankit.appui.R
import com.ankit.appui.Screen
import com.ankit.appui.ui.theme.*
import kotlinx.coroutines.flow.emptyFlow

// Use this custom implementation for blur until we can properly import the library
@Composable
private fun Modifier.blur(radius: androidx.compose.ui.unit.Dp): Modifier {
    // This is a simplified implementation that adds a semi-transparent overlay
    // to mimic a blur effect until we can properly import the blur library
    return this.then(
        Modifier.drawWithContent {
            drawContent()
            drawRect(
                color = Color.Black.copy(alpha = 0.1f),
                size = size
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(onProfileClick: () -> Unit = {}) {
    Surface(
        color = AlmostBlack,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
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
            
            // Data freshness indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Last updated: Today at 10:45 AM",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = LightGray.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@Composable
fun InsightsTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Overview", "Insights", "Direct", "Conversations", "Content")
    
    Surface(
        color = AlmostBlack,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(3.dp)
                                .padding(horizontal = 16.dp)
                                .background(
                                    color = TwitchPurple,
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                        )
                    }
                },
                divider = {},
                containerColor = AlmostBlack,
                contentColor = OffWhite,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    CustomTab(
                        title = title,
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) }
                    )
                }
            }
            
            Divider(
                color = Color.DarkGray.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Custom NoRippleInteractionSource to disable ripple effects
 */
private class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions = emptyFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

@Composable
private fun rememberedNoRippleInteractionSource() = remember { NoRippleInteractionSource() }

@Composable
fun CustomTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) OffWhite else LightGray.copy(alpha = 0.7f)
    
    Tab(
        selected = selected,
        onClick = onClick,
        interactionSource = rememberedNoRippleInteractionSource(), // Disable ripple
        text = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = color,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
    )
}

@Composable
fun MainContent(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = CardDimensions.standardPadding)
    ) {
        Spacer(modifier = Modifier.height(CardDimensions.standardSpacing)) 

        // Top row: Geo and Demographic cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CardDimensions.standardSpacing) 
        ) {
            Box(modifier = Modifier.weight(1f)) {
                GeoCard(onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "geo")) })
            }
            Box(modifier = Modifier.weight(1f)) {
                DemographicCard(onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "demographic")) })
            }
        }
        
        Spacer(modifier = Modifier.height(CardDimensions.standardSpacing))
        
        // Second row: Audience activity and Followers online
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CardDimensions.standardSpacing)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                AudienceActivityCard(onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "activity")) })
            }
            Box(modifier = Modifier.weight(1f)) {
                FollowersOnlineCard(onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "followers")) })
            }
        }
        
        Spacer(modifier = Modifier.height(CardDimensions.standardSpacing))

        // Engagement section
        Text(
            text = "Engagement",
            style = MaterialTheme.typography.titleLarge.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Engagement metrics row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CardDimensions.standardSpacing)
        ) {
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_lightning),
                title = "ER",
                value = "11.3%",
                changePercentage = "1.3%",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "er")) }
            )
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_reach),
                title = "ER Reach",
                value = "7.2%",
                changePercentage = "0.3%",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "er-reach")) }
            )
            EngagementMetricCard(
                icon = painterResource(id = R.drawable.ic_er_views),
                title = "ER Views",
                value = "5.3%",
                changePercentage = "0.2%",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Detail.route.replace("{cardId}", "er-views")) }
            )
        }
        
        Spacer(modifier = Modifier.height(CardDimensions.standardSpacing))

        // Insight Report banner
        InsightReportBanner()

        // Bottom spacing for scrolling to ensure content doesn't get hidden behind nav bar
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(1) } // Default to Insights tab
    var selectedBottomNavIndex by remember { mutableStateOf(1) } // Default to Home
    
    AppUITheme {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .background(AlmostBlack)
                        .statusBarsPadding()
                ) {
                    CustomTopAppBar { navController.navigate(Screen.Profile.route) }
                    InsightsTabRow(
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it }
                    )
                }
            },
            bottomBar = { 
                Box(modifier = Modifier.navigationBarsPadding()) {
                    CustomBottomNavigation(
                        selectedItemIndex = selectedBottomNavIndex,
                        onItemClick = { 
                            selectedBottomNavIndex = it
                            // Navigate based on bottom nav selection
                            when(it) {
                                0 -> { /* Handle Create */ }
                                1 -> { /* Stay on Home/Insights */ }
                                2 -> { navController.navigate(Screen.Settings.route) }
                            }
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            containerColor = AlmostBlack
        ) { paddingValues ->
            MainContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InsightsScreenPreview() {
    AppUITheme {
        InsightsScreen(rememberNavController())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CustomTopAppBarPreview() {
    AppUITheme {
        CustomTopAppBar()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun InsightsTabRowPreview() {
    AppUITheme {
        var selectedTabIndex by remember { mutableStateOf(1) }
        InsightsTabRow(selectedTabIndex = selectedTabIndex, onTabSelected = {selectedTabIndex = it})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun CustomBottomNavigationPreview() {
    AppUITheme {
        var selectedItemIndex by remember { mutableStateOf(1) }
        CustomBottomNavigation(selectedItemIndex = selectedItemIndex, onItemClick = {selectedItemIndex = it})
    }
}

// Standard card dimensions for consistency
object CardDimensions {
    // Standard width for all cards
    val standardWidth = Modifier.fillMaxWidth()
    
    // Card heights - made more precise to eliminate empty space
    val largeCardHeight = 176.dp  // 8dp × 22
    val mediumCardHeight = 144.dp // 8dp × 18
    val smallCardHeight = 112.dp  // 8dp × 14
    
    // UI properties
    val standardElevation = 2.dp
    val standardCornerRadius = 16.dp
    val standardPadding = 16.dp     // 8dp × 2
    val standardSpacing = 8.dp      // Standardized to 8dp grid
    
    // Content specific
    val titleHeight = 32.dp         // 8dp × 4
    val valueTextSize = 32.sp       // 8dp × 4
    val mainContentHeight = 80.dp   // 8dp × 10
    
    // Icon dimensions
    val largeIconSize = 32.dp       // 8dp × 4
    val standardIconSize = 24.dp    // 8dp × 3
    val smallIconSize = 16.dp       // 8dp × 2
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
fun GeoDistributionCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
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
        Column(
            modifier = Modifier.padding(CardDimensions.standardPadding)
        ) {
            // Header with icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.largeIconSize)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Geographic location data showing 97% followers from USA",
                        tint = LightGray,
                        modifier = Modifier.size(CardDimensions.smallIconSize)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Geo Distribution",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = OffWhite
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Map visualization placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                TwitchPurple.copy(alpha = 0.3f),
                                DarkGray.copy(alpha = 0.1f)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                // Map visualization would go here
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CountryStatItem(country = "USA", percentage = "72%")
                CountryStatItem(country = "UK", percentage = "15%")
                CountryStatItem(country = "Other", percentage = "13%")
            }
        }
    }
}

@Composable
private fun CountryStatItem(country: String, percentage: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = percentage,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = country,
            style = MaterialTheme.typography.bodySmall.copy(
                color = LightGray
            )
        )
    }
}

@Composable
fun EngagementMetricCard(
    icon: androidx.compose.ui.graphics.painter.Painter,
    title: String,
    value: String,
    changePercentage: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CardDimensions.smallCardHeight)
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
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = title,
                        tint = LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = LightGray,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            
            // Value and change - positioned at bottom
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                // Value with large font
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OffWhite
                    )
                )
                
                // Change percentage with arrow
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Increasing",
                        tint = PositiveGreen,
                        modifier = Modifier.size(12.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(2.dp))
                    
                    Text(
                        text = changePercentage,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PositiveGreen
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EngagementMetricsRow(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EngagementMetricCard(
            icon = painterResource(id = R.drawable.ic_er_lightning),
            title = "ER",
            value = "11.3%",
            changePercentage = "1.3%",
            modifier = Modifier.weight(1f),
            onClick = { onClick("engagement-er") }
        )
        EngagementMetricCard(
            icon = painterResource(id = R.drawable.ic_er_reach),
            title = "ER Reach",
            value = "7.2%",
            changePercentage = "0.3%",
            modifier = Modifier.weight(1f),
            onClick = { onClick("engagement-reach") }
        )
        EngagementMetricCard(
            icon = painterResource(id = R.drawable.ic_er_views),
            title = "ER Views",
            value = "5.3%",
            changePercentage = "0.2%",
            modifier = Modifier.weight(1f),
            onClick = { onClick("engagement-views") }
        )
    }
}

@Composable
fun AudienceActivityCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CardDimensions.mediumCardHeight)
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
        Column(
            modifier = Modifier.padding(CardDimensions.standardPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardDimensions.titleHeight)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.largeIconSize)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Timeline,
                        contentDescription = "Audience activity data showing best time at 5-6pm",
                        tint = LightGray,
                        modifier = Modifier.size(CardDimensions.smallIconSize)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Audience activity",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = OffWhite
                    )
                )
            }
            
            // Main content - centered in the available space
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Value display
                Text(
                    text = "5pm",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = OffWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = CardDimensions.valueTextSize
                    )
                )
                
                // Description text
                Text(
                    text = "Best time to post: 5-6pm",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGray,
                        fontSize = 13.sp
                    )
                )
            }
            
            // Activity mini-chart
            LinearProgressIndicator(
                progress = 0.7f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
                color = TwitchPurple,
                trackColor = TwitchPurple.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
fun FollowersOnlineCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CardDimensions.mediumCardHeight)
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
        Column(
            modifier = Modifier.padding(CardDimensions.standardPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardDimensions.titleHeight)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.largeIconSize)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PeopleAlt,
                        contentDescription = "Followers online data peaked at 3-4pm",
                        tint = LightGray,
                        modifier = Modifier.size(CardDimensions.smallIconSize)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Followers online",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = OffWhite
                    )
                )
            }
            
            // Main content
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Followers count
                Text(
                    text = "3-4pm",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = OffWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = CardDimensions.valueTextSize
                    )
                )
                
                // Description text
                Text(
                    text = "Followers most active: 3-4pm",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGray,
                        fontSize = 13.sp
                    )
                )
            }
            
            // Online followers graph with fixed height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp) // Reduced to eliminate empty space
            ) {
                FollowersGraph()
            }
        }
    }
}

@Composable
private fun FollowersGraph() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val graphPath = Path()
            
            val points = listOf(
                Offset(0f, height * 0.8f),
                Offset(width * 0.2f, height * 0.7f),
                Offset(width * 0.4f, height * 0.2f),
                Offset(width * 0.6f, height * 0.1f),
                Offset(width * 0.8f, height * 0.5f),
                Offset(width, height * 0.8f)
            )
            
            // Draw subtle grid lines
            val gridColor = LightGray.copy(alpha = 0.1f)
            val gridDashes = floatArrayOf(4f, 4f)
            
            // Horizontal grid lines (25%, 50%, 75%)
            listOf(0.25f, 0.5f, 0.75f).forEach { yPosition ->
                drawLine(
                    color = gridColor,
                    start = Offset(0f, height * yPosition),
                    end = Offset(width, height * yPosition),
                    strokeWidth = 1f,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(gridDashes)
                )
            }
            
            // Vertical grid lines (noon, 3pm, 6pm, 9pm)
            listOf(0.25f, 0.5f, 0.75f).forEach { xPosition ->
                drawLine(
                    color = gridColor,
                    start = Offset(width * xPosition, 0f),
                    end = Offset(width * xPosition, height),
                    strokeWidth = 1f,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(gridDashes)
                )
            }
            
            // Start path
            graphPath.moveTo(points[0].x, points[0].y)
            
            // Draw curved line through points
            for (i in 0 until points.size - 1) {
                val control1 = Offset(
                    x = points[i].x + (points[i+1].x - points[i].x) / 2,
                    y = points[i].y
                )
                val control2 = Offset(
                    x = points[i].x + (points[i+1].x - points[i].x) / 2,
                    y = points[i+1].y
                )
                graphPath.cubicTo(
                    control1.x, control1.y,
                    control2.x, control2.y,
                    points[i+1].x, points[i+1].y
                )
            }
            
            // Draw line
            drawPath(
                path = graphPath,
                color = TwitchPurple,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            
            // Add subtle gradient fill below the line
            val fillPath = Path().apply {
                addPath(graphPath)
                lineTo(width, height)  // Bottom right
                lineTo(0f, height)     // Bottom left
                close()
            }
            
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TwitchPurple.copy(alpha = 0.2f),
                        TwitchPurple.copy(alpha = 0.05f)
                    ),
                    startY = height * 0.2f,
                    endY = height
                )
            )
            
            // Draw point indicators
            points.forEach { point ->
                // Draw small circle at data point
                if (point.x == width * 0.4f || point.x == width * 0.6f) {
                    // Highlight peak points
                    drawCircle(
                        color = TwitchPurple.copy(alpha = 0.3f),
                        radius = 5.dp.toPx(),
                        center = point
                    )
                    drawCircle(
                        color = TwitchPurple,
                        radius = 2.5.dp.toPx(),
                        center = point
                    )
                }
            }
            
            // Add peak marker at 3-4pm (highlight the peak)
            val peakPoint = points[3]  // The highest point
            drawLine(
                color = TwitchPurple.copy(alpha = 0.5f),
                start = Offset(peakPoint.x, height),
                end = peakPoint,
                strokeWidth = 1.dp.toPx(),
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(gridDashes)
            )
        }
        
        // Time labels beneath the chart
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "12pm",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGray.copy(alpha = 0.7f)
                )
            )
            Text(
                text = "3-4pm",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TwitchPurple
                )
            )
            Text(
                text = "8pm",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGray.copy(alpha = 0.7f)
                )
            )
        }
    }
}

@Composable
fun GeoCard(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CardDimensions.mediumCardHeight)
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
        Column(
            modifier = Modifier.padding(CardDimensions.standardPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardDimensions.titleHeight)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.largeIconSize)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Geographic location data showing 97% followers from USA",
                        tint = LightGray,
                        modifier = Modifier.size(CardDimensions.smallIconSize)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Geo",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = OffWhite
                    )
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // US Flag
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.standardIconSize)
                        .clip(CircleShape)
                ) {
                    // US Flag with proper colors
                    Image(
                        painter = painterResource(id = R.drawable.ic_us_flag),
                        contentDescription = "US Flag",
                        modifier = Modifier.size(CardDimensions.standardIconSize)
                    )
                }
            }
            
            // Main content - centered in the available space
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Large percentage value
                Text(
                    text = "97%",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = OffWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = CardDimensions.valueTextSize
                    )
                )
                
                // Description text
                Text(
                    text = "Most of the followers are from USA",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGray,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
}

@Composable
fun DemographicCard(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CardDimensions.mediumCardHeight)
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
        Column(
            modifier = Modifier.padding(CardDimensions.standardPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with icon and title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardDimensions.titleHeight)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(CardDimensions.largeIconSize)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = "Demographics information for audience age groups",
                        tint = LightGray,
                        modifier = Modifier.size(CardDimensions.smallIconSize)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Demographic",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            // Age ring - fixed height to avoid empty space
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardDimensions.mainContentHeight)
            ) {
                AgeRingChart()
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "23",
                        style = MaterialTheme.typography.displayLarge
                    )
                    
                    Text(
                        text = "years old",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = LightGray
                        )
                    )
                }
            }
            
            // Age distribution label
            Text(
                text = "Average audience age: 18-29",
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AgeRingChart() {
    Canvas(
        modifier = Modifier.size(110.dp)
    ) {
        val strokeWidth = 12.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2
        
        // Draw background ring (lighter)
        drawArc(
            color = TwitchPurple.copy(alpha = 0.2f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        
        // Draw age progress (75% filled)
        drawArc(
            color = TwitchPurple,
            startAngle = 0f,
            sweepAngle = 270f, // 75% of audience in 18-29 range
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun InsightReportBanner(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CardDimensions.standardCornerRadius)),
        colors = CardDefaults.cardColors(
            containerColor = TwitchPurple.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = CardDimensions.standardElevation
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardDimensions.standardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Insights,
                contentDescription = "Insight Report",
                tint = TwitchPurple,
                modifier = Modifier.size(CardDimensions.standardIconSize)
            )
            
            Spacer(modifier = Modifier.width(CardDimensions.standardSpacing))
            
            Column {
                Text(
                    text = "Weekly Insight Report",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = OffWhite,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Text(
                    text = "Your new report is ready",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGray
                    )
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "View Report",
                tint = TwitchPurple,
                modifier = Modifier.size(CardDimensions.standardIconSize)
            )
        }
    }
}

@Composable
fun CustomBottomNavigation(selectedItemIndex: Int, onItemClick: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Filled.Home),
        BottomNavItem("AI Hub", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome)
    )

    // Custom shape for bottom navigation with rounded top corners
    val navShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = navShape,
        color = AlmostBlack, // Fixed color to match the rest of the UI
        tonalElevation = 4.dp 
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Use a solid background instead of gradient for better color matching
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(AlmostBlack)
            )
            
            // Navigation Items with Create action in toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // First half - Home nav item
                BottomNavItem(
                    item = items[0],
                    isSelected = selectedItemIndex == 1,
                    onItemClick = { onItemClick(1) }
                )
                
                // Center - Create action
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(TwitchPurple, TwitchBlue),
                                start = Offset(0f, 0f),
                                end = Offset(56f, 56f)
                            )
                        )
                        .clickable { onItemClick(0) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Create new content button",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                // Second half - AI Hub nav item
                BottomNavItem(
                    item = items[1],
                    isSelected = selectedItemIndex == 2,
                    onItemClick = { onItemClick(2) }
                )
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
    // Define colors based on selection - using the same TwitchPurple as tab indicator
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