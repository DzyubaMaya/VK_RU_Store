package ru.rustore.mvp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import ru.rustore.mvp.data.model.AppCategory
import ru.rustore.mvp.data.model.AppItem

@Composable
fun AppIcon(
    app: AppItem,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
) {
    val letter = app.title.firstOrNull()?.uppercaseChar()?.toString().orEmpty()
    val cornerRadius = size / 4
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color(app.category.brandColor)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value * 0.45f).sp,
        )
    }
}

@Composable
fun CategoryDot(
    category: AppCategory,
    modifier: Modifier = Modifier,
    size: Dp = 10.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size))
            .background(Color(category.brandColor)),
    )
}

@Suppress("unused")
@Composable
private fun previewSurface() {
    MaterialTheme {}
}
