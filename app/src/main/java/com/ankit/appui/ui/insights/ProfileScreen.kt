package com.ankit.appui.ui.insights

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ankit.appui.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    
    // Animation states
    var showHeader by remember { mutableStateOf(false) }
    var showStats by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    
    // Trigger animations sequentially
    LaunchedEffect(Unit) {
        delay(100)
        showHeader = true
        delay(200)
        showStats = true
        delay(150)
        showSettings = true
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = OffWhite
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = OffWhite
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = OffWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AlmostBlack
                )
            )
        },
        containerColor = AlmostBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Profile header
            AnimatedVisibility(
                visible = showHeader,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                ProfileHeader()
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile stats
            AnimatedVisibility(
                visible = showStats,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                ProfileStats()
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile settings
            AnimatedVisibility(
                visible = showSettings,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                ProfileOptions(navController)
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            TwitchPurple.copy(alpha = 0.7f),
                            TwitchBlue.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            
            // Profile avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(LightPurple)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = OffWhite,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Profile info
            Text(
                text = "TwitchStream42",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Content Creator",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = LightGray
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Profile bio
            Text(
                text = "Gaming and tech enthusiast. Streaming 3 times a week!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OffWhite
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Profile buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileButton(
                    icon = Icons.Default.Edit,
                    text = "Edit Profile",
                    onClick = { /* Edit profile */ }
                )
                
                ProfileButton(
                    icon = Icons.Default.Share,
                    text = "Share",
                    onClick = { /* Share profile */ }
                )
            }
        }
    }
}

@Composable
fun ProfileButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TwitchPurple
        ),
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = OffWhite,
            modifier = Modifier.size(16.dp)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = OffWhite
            )
        )
    }
}

@Composable
fun ProfileStats() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stats",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "Followers", value = "12.4K")
                StatItem(label = "Following", value = "243")
                StatItem(label = "Stream Time", value = "1.4K hrs")
            }
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGray
            )
        )
    }
}

@Composable
fun ProfileOptions(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Account",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Options
            ProfileOptionItem(
                icon = Icons.Default.Person,
                text = "Account Settings",
                onClick = { navController.navigate("settings") }
            )
            
            ProfileOptionItem(
                icon = Icons.Default.Notifications,
                text = "Notifications",
                onClick = { /* Navigate to notifications */ }
            )
            
            ProfileOptionItem(
                icon = Icons.Default.Security,
                text = "Privacy & Security",
                onClick = { /* Navigate to privacy settings */ }
            )
            
            ProfileOptionItem(
                icon = Icons.Default.HelpCenter,
                text = "Help & Support",
                onClick = { /* Navigate to help */ }
            )
            
            ProfileOptionItem(
                icon = Icons.Default.Logout,
                text = "Logout",
                onClick = { /* Logout */ },
                textColor = TwitchPink
            )
        }
    }
}

@Composable
fun ProfileOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    textColor: Color = OffWhite
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true, color = TwitchPurple),
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = textColor
            )
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = LightGray,
            modifier = Modifier.size(16.dp)
        )
    }
} 