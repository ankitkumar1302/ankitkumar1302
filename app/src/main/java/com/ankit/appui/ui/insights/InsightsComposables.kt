package com.ankit.appui.ui.insights

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OnlinePrediction
import androidx.compose.material.icons.filled.People
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.appui.R
import com.ankit.appui.ui.theme.AppUITheme
import com.ankit.appui.ui.theme.CardTitleTextStyle
import com.ankit.appui.ui.theme.ChangePercentageTextStyle
import com.ankit.appui.ui.theme.DarkGray
import com.ankit.appui.ui.theme.DarkPurple
import com.ankit.appui.ui.theme.InsightCardCornerRadius
import com.ankit.appui.ui.theme.LargeMetricTextStyle
import com.ankit.appui.ui.theme.LightGray
import com.ankit.appui.ui.theme.LightPurple
import com.ankit.appui.ui.theme.OffWhite
import com.ankit.appui.ui.theme.PositiveGreen
import com.ankit.appui.ui.theme.SmallBodyTextStyle
import com.ankit.appui.ui.theme.TwitchPurple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GeoCard(onClick: () -> Unit = {}) {
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    // Animate the card appearance
    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                )
            ) {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.95f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(100)
                    )
                    onClick()
                }
            },
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with icon and flag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Location icon with subtle background
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Geo Icon",
                        tint = LightGray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Geo",
                    style = CardTitleTextStyle.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                // US Flag with circle background
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_us_flag),
                        contentDescription = "US Flag",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large percentage value
            Text(
                text = "97%",
                style = LargeMetricTextStyle.copy(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Description text
            Text(
                text = "Most of your followers are from the USA",
                style = SmallBodyTextStyle.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Composable
fun DemographicCard(onClick: () -> Unit = {}) {
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    // Animate the card appearance with a slight delay
    LaunchedEffect(Unit) {
        delay(100) // Slight delay for staggered effect
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                )
            ) {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.95f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(100)
                    )
                    onClick()
                }
            },
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.People,
                    contentDescription = "Demographic Icon",
                    tint = LightGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Demographic", style = CardTitleTextStyle)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Enhanced Donut Chart implementation
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp) // Slightly taller for better proportions
            ) {
                // The donut chart - using single color to avoid false gender split affordance
                Canvas(modifier = Modifier.size(120.dp)) {
                    val strokeWidth = 18f

                    // Background circle (light gray background)
                    drawArc(
                        color = Color.DarkGray.copy(alpha = 0.3f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Single color arc for age group representation
                    drawArc(
                        color = TwitchPurple,
                        startAngle = -90f,
                        sweepAngle = 270f, // 75% of the circle
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Add small indicators
                    val radius = size.width / 2 - strokeWidth / 2

                    // Start indicator
                    val startPoint = Offset(
                        x = size.width / 2,
                        y = size.height / 2 - radius
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3f,
                        center = startPoint
                    )

                    // End indicator at 75%
                    val endAngleRadians = Math.toRadians((-90f + 270f).toDouble())
                    val endPoint = Offset(
                        x = (size.width / 2 + radius * Math.cos(endAngleRadians)).toFloat(),
                        y = (size.height / 2 + radius * Math.sin(endAngleRadians)).toFloat()
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3f,
                        center = endPoint
                    )
                }

                // Central text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "23",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = OffWhite
                        )
                    )
                    Text(
                        text = "years old",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LightGray,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Age group label (replaced gender labels)
            Text(
                text = "Average audience age: 18-29",
                style = SmallBodyTextStyle.copy(
                    color = LightGray,
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AudienceActivityCard(onClick: () -> Unit = {}) {
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    // Animate the card appearance with a slight delay
    LaunchedEffect(Unit) {
        delay(200) // More delay for staggered effect
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                )
            ) {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.95f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(100)
                    )
                    onClick()
                }
            },
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Clock icon with subtle background
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = "Audience Activity Icon",
                        tint = LightGray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Audience activity",
                    style = CardTitleTextStyle.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary metric value
            Text(
                text = "5pm",
                style = LargeMetricTextStyle.copy(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time slider visualization
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.DarkGray.copy(alpha = 0.5f))
            ) {
                // Active segment (5pm-6pm, roughly 1/3 of the way from right)
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(4.dp)
                        .offset(x = 80.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TwitchPurple)
                )

                // Indicator dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .offset(x = 120.dp, y = -2.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description text
            Text(
                text = "Best time to post: 5-6pm",
                style = SmallBodyTextStyle.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Composable
fun FollowersOnlineCard(onClick: () -> Unit = {}) {
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    // Animate the card appearance with a slight delay
    LaunchedEffect(Unit) {
        delay(300) // Even more delay for staggered effect
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                )
            ) {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.95f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(100)
                    )
                    onClick()
                }
            },
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.OnlinePrediction,
                    contentDescription = "Followers Online Icon",
                    tint = LightGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Followers online", style = CardTitleTextStyle)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Primary metric value
            Text(
                "3-4pm",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Enhanced Line Graph with animation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                // Line chart with gradient fill
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Data points (normalized to canvas height)
                    val points = listOf(
                        Offset(size.width * 0.0f, size.height * 0.7f),  // Start
                        Offset(size.width * 0.1f, size.height * 0.75f),
                        Offset(size.width * 0.2f, size.height * 0.6f),
                        Offset(size.width * 0.3f, size.height * 0.3f),  // Rising
                        Offset(size.width * 0.4f, size.height * 0.2f),  // Peak
                        Offset(size.width * 0.5f, size.height * 0.3f),  // Falling
                        Offset(size.width * 0.6f, size.height * 0.5f),
                        Offset(size.width * 0.8f, size.height * 0.75f),
                        Offset(size.width * 1.0f, size.height * 0.7f)   // End
                    )

                    // Create a smooth path through the points
                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)

                        // Use quadratic bezier curves to create smooth transitions
                        for (i in 1 until points.size) {
                            val prevPoint = points[i - 1]
                            val currentPoint = points[i]

                            // Calculate control point as midpoint
                            val controlX = (prevPoint.x + currentPoint.x) / 2
                            val controlY = (prevPoint.y + currentPoint.y) / 2

                            // Add the curve
                            quadraticBezierTo(
                                controlX, controlY,
                                currentPoint.x, currentPoint.y
                            )
                        }
                    }

                    // Draw the line with a thicker, smoother stroke
                    drawPath(
                        path = path,
                        color = TwitchPurple,
                        style = Stroke(
                            width = 3.5f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    // Create a filled path for the gradient area below the line
                    val fillPath = Path().apply {
                        addPath(path)
                        lineTo(size.width, size.height)  // Bottom right
                        lineTo(0f, size.height)          // Bottom left
                        close()
                    }

                    // Draw the gradient fill below the line
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                TwitchPurple.copy(alpha = 0.4f),
                                TwitchPurple.copy(alpha = 0.1f),
                                TwitchPurple.copy(alpha = 0.0f)
                            ),
                            startY = size.height * 0.2f,  // Start near the peak
                            endY = size.height
                        )
                    )

                    // Draw indicator dots at key points
                    val keyPoints = listOf(
                        points[0],    // Start
                        points[4],    // Peak (3-4pm)
                        points[8]     // End
                    )

                    keyPoints.forEach { point ->
                        // Larger outer circle (glow effect)
                        drawCircle(
                            color = TwitchPurple.copy(alpha = 0.3f),
                            radius = 8f,
                            center = point
                        )

                        // White inner circle
                        drawCircle(
                            color = Color.White,
                            radius = 4f,
                            center = point
                        )
                    }

                    // Highlight the peak point with a larger indicator
                    drawCircle(
                        color = TwitchPurple.copy(alpha = 0.3f),
                        radius = 10f,
                        center = points[4]
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 5f,
                        center = points[4]
                    )

                    // Draw time ticks with labels
                    // Morning tick
                    drawLine(
                        color = LightGray.copy(alpha = 0.5f),
                        start = Offset(size.width * 0.2f, size.height * 0.9f),
                        end = Offset(size.width * 0.2f, size.height * 0.85f),
                        strokeWidth = 1.5f
                    )
                    // Peak tick (3-4pm)
                    drawLine(
                        color = TwitchPurple.copy(alpha = 0.8f),
                        start = Offset(size.width * 0.4f, size.height * 0.9f),
                        end = Offset(size.width * 0.4f, size.height * 0.85f),
                        strokeWidth = 1.5f
                    )
                    // Evening tick
                    drawLine(
                        color = LightGray.copy(alpha = 0.5f),
                        start = Offset(size.width * 0.8f, size.height * 0.9f),
                        end = Offset(size.width * 0.8f, size.height * 0.85f),
                        strokeWidth = 1.5f
                    )
                }

                // Time labels beneath the chart
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "12pm",
                        style = SmallBodyTextStyle.copy(
                            fontSize = 10.sp,
                            color = LightGray.copy(alpha = 0.7f)
                        )
                    )
                    Text(
                        text = "3-4pm",
                        style = SmallBodyTextStyle.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = TwitchPurple
                        )
                    )
                    Text(
                        text = "8pm",
                        style = SmallBodyTextStyle.copy(
                            fontSize = 10.sp,
                            color = LightGray.copy(alpha = 0.7f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EngagementSectionTitle() {
    Text(
        "Engagement",
        style = MaterialTheme.typography.titleMedium.copy(
            color = OffWhite,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

@Composable
fun EngagementMetricCard(
    icon: Painter,
    title: String,
    value: String,
    changePercentage: String,
    isPositive: Boolean = true,
    modifier: Modifier = Modifier
) {
    EngagementMetricCardInternal(
        icon = icon,
        title = title,
        value = value,
        changePercentage = changePercentage,
        isPositive = isPositive,
        modifier = modifier
    )
}

@Composable
fun EngagementMetricCardInternal(
    icon: Painter,
    title: String,
    value: String,
    changePercentage: String,
    isPositive: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 2.dp),
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Icon and title row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Custom icon container with subtle background
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(LightGray.copy(alpha = 0.1f))
                        .padding(5.dp)
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
                    style = SmallBodyTextStyle.copy(
                        color = OffWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Value display with large, bold text
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = value,
                    style = LargeMetricTextStyle.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Change percentage indicator - centered directly beneath the value
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val changeColor = if (isPositive) PositiveGreen else Color.Red

                    // Arrow icon
                    Icon(
                        imageVector = if (isPositive) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = "Change ${if (isPositive) "Up" else "Down"}",
                        tint = changeColor,
                        modifier = Modifier.size(18.dp)
                    )

                    // Change percentage text
                    Text(
                        text = changePercentage,
                        style = ChangePercentageTextStyle.copy(
                            color = changeColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun InsightReportBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon for the report
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightGray.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Description,
                    contentDescription = "Report Icon",
                    tint = OffWhite,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Insight Report",
                    style = CardTitleTextStyle.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Download & share your social media report with clients or advertisers",
                    style = SmallBodyTextStyle.copy(
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Download button with purple gradient
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(LightPurple, DarkPurple),
                            start = Offset(0f, 0f),
                            end = Offset(44f, 44f)
                        )
                    )
                    .clickable { /* Handle download action */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Download Report",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EngagementMetricCardWithClick(
    icon: Painter,
    title: String,
    value: String,
    changePercentage: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = TwitchPurple
                )
            ) {
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 0.95f,
                        animationSpec = tween(100)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(100)
                    )
                    onClick()
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = LightGray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightGray
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = OffWhite,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isPositive = !changePercentage.startsWith("-")
                val trendIcon =
                    if (isPositive) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
                val trendColor = if (isPositive) PositiveGreen else Color.Red

                Icon(
                    imageVector = trendIcon,
                    contentDescription = if (isPositive) "Increase" else "Decrease",
                    tint = trendColor,
                    modifier = Modifier.size(12.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = changePercentage,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = trendColor
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun GeoCardPreview() {
    AppUITheme { GeoCard() }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun DemographicCardPreview() {
    AppUITheme { DemographicCard() }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun AudienceActivityCardPreview() {
    AppUITheme { AudienceActivityCard() }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun FollowersOnlineCardPreview() {
    AppUITheme { FollowersOnlineCard() }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun EngagementMetricCardPreview() {
    AppUITheme {
        Row(Modifier.padding(16.dp)) {
            EngagementMetricCardInternal(
                icon = painterResource(id = R.drawable.ic_er_lightning),
                title = "ER",
                value = "11.3%",
                changePercentage = "1.32%",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun InsightReportBannerPreview() {
    AppUITheme { InsightReportBanner() }
} 