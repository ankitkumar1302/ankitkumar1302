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
fun CustomTopAppBar() {
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
            // Profile icon/avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(TwitchPurple.copy(alpha = 0.7f)),
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
        color = DarkGray,
        shadowElevation = 8.dp // Add elevation for shadow effect
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
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
    
    // Custom nav item with circle background when selected
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (isSelected) TwitchPurple.copy(alpha = 0.1f) else Color.Transparent)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = item.selectedIcon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = item.label,
            style = BottomNavTextStyle.copy(
                color = textColor,
                fontWeight = if(isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            fontSize = 10.sp
        )
    }
}

data class BottomNavItem(val label: String, val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector, val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector)

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