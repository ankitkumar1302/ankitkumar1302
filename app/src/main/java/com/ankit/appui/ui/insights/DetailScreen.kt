package com.ankit.appui.ui.insights

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ankit.appui.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, cardId: String) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    
    // Animation states
    var showContent by remember { mutableStateOf(false) }
    var showGraph by remember { mutableStateOf(false) }
    var showStats by remember { mutableStateOf(false) }
    
    val titleBasedOnCardId = when (cardId) {
        "demographics" -> "Audience Demographics"
        "geo" -> "Geographic Distribution"
        "audience" -> "Audience Activity"
        "followers" -> "Followers Online"
        else -> "Engagement Metrics"
    }
    
    // Trigger animations sequentially
    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
        delay(300)
        showGraph = true
        delay(150)
        showStats = true
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = titleBasedOnCardId,
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
                    IconButton(onClick = { /* Share functionality */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = OffWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AlmostBlack,
                    titleContentColor = OffWhite
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
            // Animated content entry
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkGray
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Card content based on cardId
                        when (cardId) {
                            "demographics" -> DetailedDemographicsContent(showGraph, showStats)
                            "geo" -> DetailedGeoContent(showGraph, showStats)
                            "audience" -> DetailedAudienceContent(showGraph, showStats)
                            "followers" -> DetailedFollowersContent(showGraph, showStats)
                            else -> DetailedEngagementContent(cardId, showGraph, showStats)
                        }
                    }
                }
            }
            
            // Additional related metrics section
            AnimatedVisibility(
                visible = showStats,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing, delayMillis = 300)
                )
            ) {
                RelatedMetricsSection(cardId)
            }
        }
    }
}

@Composable
fun DetailedDemographicsContent(showGraph: Boolean, showStats: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Audience Demographics",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Detailed breakdown of your audience by gender and age",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGray
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Enhanced Donut Chart with animation
        if (showGraph) {
            EnhancedDemographicsDonut()
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Demographics stats
        if (showStats) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DemographicStat(label = "Male", value = "58%", color = TwitchBlue)
                DemographicStat(label = "Female", value = "39%", color = TwitchPink)
                DemographicStat(label = "Other", value = "3%", color = PositiveGreen)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Age Distribution",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            AgeDistributionGraph()
        }
    }
}

@Composable
fun EnhancedDemographicsDonut() {
    // Animation progress
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "DonutProgress"
    )
    
    // Start animation
    LaunchedEffect(Unit) {
        progress = 1f
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(240.dp)
                .padding(16.dp)
        ) {
            val width = size.width
            val height = size.height
            val thickness = width * 0.15f
            val radius = (width / 2) - thickness
            
            // Draw Male segment (58%)
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TwitchBlue.copy(alpha = 0.7f),
                        TwitchBlue
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(width, height)
                ),
                startAngle = 0f,
                sweepAngle = 360f * 0.58f * animatedProgress,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )
            
            // Draw Female segment (39%)
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TwitchPink.copy(alpha = 0.7f),
                        TwitchPink
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(width, height)
                ),
                startAngle = 360f * 0.58f * animatedProgress,
                sweepAngle = 360f * 0.39f * animatedProgress,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )
            
            // Draw Other segment (3%)
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        PositiveGreen.copy(alpha = 0.7f),
                        PositiveGreen
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(width, height)
                ),
                startAngle = 360f * (0.58f + 0.39f) * animatedProgress,
                sweepAngle = 360f * 0.03f * animatedProgress,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )
        }
        
        // Center text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "58%",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Male",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )
        }
    }
}

@Composable
fun DemographicStat(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(color)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = LightGray
            )
        )
    }
}

@Composable
fun AgeDistributionGraph() {
    // Animation progress
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "BarProgress"
    )
    
    // Start animation
    LaunchedEffect(Unit) {
        progress = 1f
    }
    
    val ageGroups = listOf("18-24", "25-34", "35-44", "45-54", "55+")
    val percentages = listOf(0.42f, 0.28f, 0.15f, 0.10f, 0.05f)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        ageGroups.forEachIndexed { index, age ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height((percentages[index] * 120 * animatedProgress).dp)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    TwitchPurple.copy(alpha = 0.7f),
                                    TwitchPurple
                                )
                            )
                        )
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = age,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LightGray,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

// Placeholder implementations for other detail types
@Composable
fun DetailedGeoContent(showGraph: Boolean, showStats: Boolean) {
    // Implement detailed geographic distribution content
    Text(
        text = "Geographic Distribution Details",
        style = MaterialTheme.typography.titleLarge.copy(color = OffWhite)
    )
    // Implementation will be similar to demographics
}

@Composable
fun DetailedAudienceContent(showGraph: Boolean, showStats: Boolean) {
    // Implement detailed audience activity content
    Text(
        text = "Audience Activity Details",
        style = MaterialTheme.typography.titleLarge.copy(color = OffWhite)
    )
    // Implementation will be similar to demographics
}

@Composable
fun DetailedFollowersContent(showGraph: Boolean, showStats: Boolean) {
    // Implement detailed followers online content
    Text(
        text = "Followers Online Details",
        style = MaterialTheme.typography.titleLarge.copy(color = OffWhite)
    )
    // Implementation will be similar to demographics
}

@Composable
fun DetailedEngagementContent(metricType: String, showGraph: Boolean, showStats: Boolean) {
    // Implement detailed engagement metrics content
    Text(
        text = "Engagement Metrics Details: $metricType",
        style = MaterialTheme.typography.titleLarge.copy(color = OffWhite)
    )
    // Implementation will be similar to demographics
}

@Composable
fun RelatedMetricsSection(cardId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Related Metrics",
            style = MaterialTheme.typography.titleLarge.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Related metrics cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Two smaller related metric cards
            RelatedMetricCard(
                title = "Growth Rate",
                value = "+12.4%",
                isPositive = true,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
            )
            
            RelatedMetricCard(
                title = "Retention",
                value = "86%",
                isPositive = true,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
            )
        }
    }
}

@Composable
fun RelatedMetricCard(
    title: String,
    value: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (isPositive) PositiveGreen else TwitchPink,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
} 