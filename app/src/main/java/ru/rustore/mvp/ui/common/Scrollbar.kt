package ru.rustore.mvp.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.verticalScrollbar(
    state: ScrollState,
    width: Dp = 4.dp,
    rightPadding: Dp = 2.dp,
    color: Color = Color(0xFF9E9E9E),
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 0.7f else 0.35f
    val alpha by animateFloatAsState(targetValue = targetAlpha, label = "scrollbarAlpha")
    return this.composed {
        Modifier.drawWithContent {
            drawContent()
            val maxScroll = state.maxValue
            if (maxScroll <= 0) return@drawWithContent
            val viewport = size.height
            val contentHeight = viewport + maxScroll
            val barHeight = (viewport * viewport / contentHeight).coerceAtLeast(24.dp.toPx())
            val barTop = (state.value.toFloat() / maxScroll) * (viewport - barHeight)
            val widthPx = width.toPx()
            drawRoundRect(
                color = color.copy(alpha = alpha),
                topLeft = Offset(size.width - widthPx - rightPadding.toPx(), barTop),
                size = Size(widthPx, barHeight),
                cornerRadius = CornerRadius(widthPx / 2),
            )
        }
    }
}
