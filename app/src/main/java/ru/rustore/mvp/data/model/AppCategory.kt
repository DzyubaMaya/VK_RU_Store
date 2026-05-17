package ru.rustore.mvp.data.model

enum class AppCategory(
    val displayName: String,
    val brandColor: Long,
) {
    FINANCE(displayName = "Финансы", brandColor = 0xFF1B7F3A),
    TOOLS(displayName = "Инструменты", brandColor = 0xFF455A64),
    GAMES(displayName = "Игры", brandColor = 0xFFE53935),
    GOVERNMENT(displayName = "Государственные", brandColor = 0xFF1565C0),
    TRANSPORT(displayName = "Транспорт", brandColor = 0xFFEF6C00),
}
