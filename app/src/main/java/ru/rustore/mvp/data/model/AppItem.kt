package ru.rustore.mvp.data.model

data class AppItem(
    val id: String,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val icon: ImageRef,
    val category: AppCategory,
    val developer: String,
    val ageRating: AgeRating,
    val screenshots: List<Screenshot>,
    val rating: Double,
    val downloads: String,
    val version: String,
    val updatedAt: String,
    val sizeMb: Int,
    val apkUrl: String? = null,
)

data class CategorySummary(
    val category: AppCategory,
    val appCount: Int,
)
