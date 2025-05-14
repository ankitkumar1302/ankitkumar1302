package com.ankit.appui.ui.insights

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ankit.appui.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, cardId: String) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Animation states
    var showContent by remember { mutableStateOf(false) }
    var showGraph by remember { mutableStateOf(false) }
    var showStats by remember { mutableStateOf(false) }

    // Fixed card ID mapping issue - match card IDs from navigation
    val titleBasedOnCardId = when (cardId) {
        "demographic" -> "Audience Demographics"
        "geo" -> "Geographic Distribution"
        "activity" -> "Audience Activity"
        "followers" -> "Followers Online"
        "er" -> "Engagement Rate"
        "er-reach" -> "Engagement Rate Reach"
        "er-views" -> "Engagement Rate Views"
        else -> "Engagement Metrics"
    }

    // Enhanced staggered animations
    LaunchedEffect(Unit) {
        delay(50) // Shorter initial delay for better UX
        showContent = true
        delay(250)
        showGraph = true
        delay(150)
        showStats = true
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(300, easing = EaseOutCubic)
                ) + slideInVertically(
                    initialOffsetY = { -it / 2 },
                    animationSpec = tween(300, easing = EaseOutCubic)
                )
            ) {
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
            }
        },
        containerColor = AlmostBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Animated content entry with improved animation specs
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500, easing = EaseOutQuint)
                ) + expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 500, easing = EaseOutQuint)
                ),
                exit = fadeOut() + shrinkVertically()
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
                        // Card content based on cardId with proper mapping
                        when (cardId) {
                            "demographic" -> DetailedDemographicsContent(showGraph, showStats)
                            "geo" -> DetailedGeoContent(showGraph, showStats)
                            "activity" -> DetailedAudienceContent(showGraph, showStats)
                            "followers" -> DetailedFollowersContent(showGraph, showStats)
                            "er", "er-reach", "er-views" -> DetailedEngagementContent(
                                cardId,
                                showGraph,
                                showStats
                            )

                            else -> DetailedEngagementContent(cardId, showGraph, showStats)
                        }
                    }
                }
            }

            // Additional related metrics section with improved animation
            AnimatedVisibility(
                visible = showStats,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = EaseOutQuint,
                        delayMillis = 300
                    )
                ) + expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = EaseOutBack,
                        delayMillis = 300
                    )
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
    Column(modifier = Modifier.fillMaxWidth()) {
        // Get metric details based on type
        val (title, description, value, trend) = when (metricType) {
            "er" -> Quadruple(
                "Engagement Rate",
                "Overall engagement across your channel",
                "11.3%",
                "+1.32%"
            )

            "er-reach" -> Quadruple(
                "Engagement Rate Reach",
                "Engagement relative to your total audience reach",
                "7.2%",
                "+0.3%"
            )

            "er-views" -> Quadruple(
                "Engagement Rate Views",
                "Engagement relative to total content views",
                "5.3%",
                "+0.2%"
            )

            else -> Quadruple(
                "Engagement Metrics",
                "Key engagement statistics for your channel",
                "8.6%",
                "+0.9%"
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Main metric with trend
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.displayLarge.copy(
                    color = TwitchPurple,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Trend Up",
                        tint = PositiveGreen,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = trend,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = PositiveGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Text(
                    text = "vs last period",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LightGray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Trend graph
        if (showGraph) {
            Text(
                text = "30 Day Trend",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            EngagementTrendGraph()
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stats
        if (showStats) {
            Text(
                text = "Engagement Breakdown",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            EngagementBreakdownList()
        }
    }
}

@Composable
fun EngagementTrendGraph() {
    // Animation progress
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1500,
            easing = EaseOutQuart
        ),
        label = "GraphProgress"
    )
    
    LaunchedEffect(Unit) {
        progress = 1f
    }
    
    val points = listOf(
        0.4f, 0.42f, 0.45f, 0.5f, 0.47f, 0.52f, 0.55f, 0.6f, 0.57f, 0.62f,
        0.65f, 0.63f, 0.67f, 0.69f, 0.72f, 0.7f, 0.76f, 0.78f, 0.8f, 0.78f,
        0.82f, 0.85f, 0.83f, 0.87f, 0.9f, 0.88f, 0.92f, 0.95f, 0.93f, 0.97f
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 8.dp)
            .drawWithContent {
                val width = size.width
                val height = size.height
                val linePathWidth = 4.dp.toPx()
                val xStep = width / (points.size - 1)
                val maxY = height * 0.8f  // Leave some space at top
                
                // Draw horizontal grid lines
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(0f, height * 0.25f),
                    end = Offset(width, height * 0.25f),
                    strokeWidth = 1.dp.toPx()
                )
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(0f, height * 0.5f),
                    end = Offset(width, height * 0.5f),
                    strokeWidth = 1.dp.toPx()
                )
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(0f, height * 0.75f),
                    end = Offset(width, height * 0.75f),
                    strokeWidth = 1.dp.toPx()
                )
                
                // Draw the trend line
                for (i in 0 until points.size - 1) {
                    if (i / (points.size - 1f) > animatedProgress) break

                    val xRatio = i.toFloat() / (points.size - 1).toFloat()
                    val nextXRatio = (i + 1).toFloat() / (points.size - 1).toFloat()

                    val visibleLength = (animatedProgress - xRatio) / (nextXRatio - xRatio)
                    val visibleLength2 = if (visibleLength > 1f) 1f else visibleLength

                    if (visibleLength <= 0) continue

                    val x1 = i * xStep
                    val y1 = height - (points[i] * maxY)

                    val x2 = x1 + (xStep * visibleLength2)
                    val y2 =
                        height - (points[i] * maxY * (1 - visibleLength2) + points[i + 1] * maxY * visibleLength2)

                    drawLine(
                        brush = Brush.linearGradient(
                            colors = listOf(TwitchPurple, TwitchBlue),
                            start = Offset(x1, y1),
                            end = Offset(x2, y2)
                        ),
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = linePathWidth,
                        cap = StrokeCap.Round
                    )

                    // Draw points
                    if (i % 5 == 0 || i == points.size - 2) {
                        drawCircle(
                            color = TwitchPurple,
                            radius = 4.dp.toPx(),
                            center = Offset(x1, y1)
                        )
                    }
                }

                drawContent()
            }
    ) {
        // Labels for the graph
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Apr 1",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LightGray,
                    fontSize = 10.sp
                )
            )

            Text(
                text = "Apr 30",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LightGray,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
fun EngagementBreakdownList() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EngagementBreakdownItem(
            label = "Likes",
            value = "24.7K",
            percentage = "62%",
            color = TwitchPurple
        )

        Spacer(modifier = Modifier.height(12.dp))

        EngagementBreakdownItem(
            label = "Comments",
            value = "8.3K",
            percentage = "21%",
            color = TwitchBlue
        )

        Spacer(modifier = Modifier.height(12.dp))

        EngagementBreakdownItem(
            label = "Shares",
            value = "4.2K",
            percentage = "11%",
            color = PositiveGreen
        )

        Spacer(modifier = Modifier.height(12.dp))

        EngagementBreakdownItem(
            label = "Saved",
            value = "2.5K",
            percentage = "6%",
            color = TwitchPink
        )
    }
}

@Composable
fun EngagementBreakdownItem(
    label: String,
    value: String,
    percentage: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(color)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGray
            ),
            modifier = Modifier.width(80.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = percentage,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = color,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

// Helper class for holding metric data
data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

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