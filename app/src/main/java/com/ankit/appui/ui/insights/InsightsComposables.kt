package com.ankit.appui.ui.insights

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.appui.R
import com.ankit.appui.ui.theme.*

@Composable
fun GeoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                text = "Most of the followers are from USA",
                style = SmallBodyTextStyle.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Composable
fun DemographicCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                // The donut chart
                Canvas(modifier = Modifier.size(120.dp)) {
                    val strokeWidth = 18f
                    val malePercentage = 0.7f  // Example: 70% male
                    val femalePercentage = 0.3f // Example: 30% female
                    
                    // Background circle (light gray background)
                    drawArc(
                        color = Color.DarkGray.copy(alpha = 0.3f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    
                    // Male segment (blue portion)
                    drawArc(
                        color = TwitchBlue,
                        startAngle = -90f,
                        sweepAngle = 360f * malePercentage,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    
                    // Female segment (pink portion)
                    drawArc(
                        brush = Brush.sweepGradient(
                            colorStops = arrayOf(
                                0.0f to TwitchBlue,
                                0.3f to TwitchPink,
                                1.0f to TwitchPink
                            ),
                            center = Offset(size.width / 2, size.height / 2)
                        ),
                        startAngle = -90f + (360f * malePercentage),
                        sweepAngle = 360f * femalePercentage,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    
                    // Draw small indicators at junction points
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
                    
                    // Junction point
                    val maleAngleRadians = Math.toRadians((-90f + 360f * malePercentage).toDouble())
                    val junctionPoint = Offset(
                        x = (size.width / 2 + radius * Math.cos(maleAngleRadians)).toFloat(),
                        y = (size.height / 2 + radius * Math.sin(maleAngleRadians)).toFloat()
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 3f,
                        center = junctionPoint
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
            
            // Labels below the chart
            Row(
                modifier = Modifier.fillMaxWidth(), 
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    "Male", 
                    style = SmallBodyTextStyle.copy(
                        color = TwitchBlue,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    "Female", 
                    style = SmallBodyTextStyle.copy(
                        color = TwitchPink,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
fun AudienceActivityCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = InsightCardCornerRadius,
        colors = CardDefaults.cardColors(containerColor = DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                text = "Best time to post today between 5 & 6pm",
                style = SmallBodyTextStyle.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Composable
fun FollowersOnlineCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                            val prevPoint = points[i-1]
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
                }
                
                // Optional: Time indicators (can be added as a Row with Text below the chart)
            }
        }
    }
}

@Composable
fun EngagementSectionTitle() {
    Text(
        "Engagement",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 16.dp)
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
            Text(
                text = value,
                style = LargeMetricTextStyle.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // Change percentage indicator
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
             EngagementMetricCard(
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