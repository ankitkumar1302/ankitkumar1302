package com.ankit.appui.ui.insights

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ankit.appui.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    
    // Animation states
    var showContent by remember { mutableStateOf(false) }
    
    // Trigger animations
    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Settings",
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AlmostBlack
                )
            )
        },
        containerColor = AlmostBlack
    ) { paddingValues ->
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Account settings
                SettingsSection(title = "Account Settings") {
                    SettingItem(
                        icon = Icons.Default.Person,
                        title = "Profile Information",
                        subtitle = "Change your profile details",
                        onClick = { /* Navigate to profile edit */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.Password,
                        title = "Password",
                        subtitle = "Change your password",
                        onClick = { /* Navigate to password change */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.Email,
                        title = "Email",
                        subtitle = "example@email.com",
                        onClick = { /* Navigate to email settings */ }
                    )
                }
                
                // Notification settings
                SettingsSection(title = "Notifications") {
                    SettingSwitchItem(
                        icon = Icons.Default.Notifications,
                        title = "Push Notifications",
                        subtitle = "Receive push notifications",
                        initialValue = true,
                        onChange = { /* Change push notification setting */ }
                    )
                    
                    SettingSwitchItem(
                        icon = Icons.Default.Email,
                        title = "Email Notifications",
                        subtitle = "Receive email notifications",
                        initialValue = false,
                        onChange = { /* Change email notification setting */ }
                    )
                    
                    SettingSwitchItem(
                        icon = Icons.Default.Sms,
                        title = "SMS Notifications",
                        subtitle = "Receive SMS notifications",
                        initialValue = false,
                        onChange = { /* Change SMS notification setting */ }
                    )
                }
                
                // Appearance settings
                SettingsSection(title = "Appearance") {
                    SettingSwitchItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        initialValue = true,
                        onChange = { /* Change theme setting */ }
                    )
                    
                    SettingDropdownItem(
                        icon = Icons.Default.Palette,
                        title = "Accent Color",
                        subtitle = "Change app accent color",
                        options = listOf("Purple (Default)", "Blue", "Green", "Pink"),
                        initialSelection = 0,
                        onChange = { /* Change accent color */ }
                    )
                }
                
                // Privacy settings
                SettingsSection(title = "Privacy & Security") {
                    SettingSwitchItem(
                        icon = Icons.Default.Security,
                        title = "Two-Factor Authentication",
                        subtitle = "Add an extra layer of security",
                        initialValue = false,
                        onChange = { /* Change 2FA setting */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.Lock,
                        title = "Privacy Settings",
                        subtitle = "Manage who can see your content",
                        onClick = { /* Navigate to privacy settings */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.DataUsage,
                        title = "Data Usage",
                        subtitle = "Manage how we use your data",
                        onClick = { /* Navigate to data settings */ }
                    )
                }
                
                // About section
                SettingsSection(title = "About") {
                    SettingItem(
                        icon = Icons.Default.Info,
                        title = "About Twitch Insights",
                        subtitle = "Version 1.0.0",
                        onClick = { /* Show about dialog */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.Help,
                        title = "Help & Support",
                        subtitle = "Get help with Twitch Insights",
                        onClick = { /* Navigate to help */ }
                    )
                    
                    SettingItem(
                        icon = Icons.Default.Feedback,
                        title = "Feedback",
                        subtitle = "Send us your feedback",
                        onClick = { /* Open feedback form */ }
                    )
                }
                
                // Danger zone
                SettingsSection(title = "Danger Zone") {
                    SettingItem(
                        icon = Icons.Default.DeleteForever,
                        title = "Delete Account",
                        subtitle = "Permanently delete your account",
                        onClick = { /* Show delete account dialog */ },
                        textColor = TwitchPink
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            content()
        }
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    textColor: Color = OffWhite
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true, color = TwitchPurple),
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = LightGray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SettingSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    initialValue: Boolean,
    onChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(initialValue) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = OffWhite,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Medium
                )
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )
        }
        
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = OffWhite,
                checkedTrackColor = TwitchPurple,
                uncheckedThumbColor = LightGray,
                uncheckedTrackColor = DarkGray
            )
        )
    }
}

@Composable
fun SettingDropdownItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    options: List<String>,
    initialSelection: Int,
    onChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(initialSelection) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = OffWhite,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Medium
                )
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )
        }
        
        Box {
            val interactionSource = remember { MutableInteractionSource() }
            
            Text(
                text = options[selectedIndex],
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TwitchPurple,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(bounded = true),
                        onClick = { expanded = true }
                    )
                    .padding(8.dp)
            )
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(DarkGray)
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (index == selectedIndex) TwitchPurple else OffWhite
                                )
                            )
                        },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                            onChange(index)
                        }
                    )
                }
            }
        }
    }
} 