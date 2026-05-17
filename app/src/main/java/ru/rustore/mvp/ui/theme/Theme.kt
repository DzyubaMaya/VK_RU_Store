package ru.rustore.mvp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RuStoreBlue,
    onPrimary = Color.White,
    primaryContainer = RuStoreBlueDark,
    secondary = RuStoreBlue,
)

@Composable
fun RuStoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}
