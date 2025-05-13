package com.ankit.appui.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)

// Custom shape for cards
val CardShapes = Shapes(
    medium = RoundedCornerShape(16.dp) // Standard rounded corner for cards
)

val InsightCardCornerRadius = RoundedCornerShape(16.dp) 