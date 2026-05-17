package ru.rustore.mvp.data.repository

import ru.rustore.mvp.data.model.AppCategory
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.model.CategorySummary

interface AppRepository {
    fun getApps(): List<AppItem>
    fun getAppById(id: String): AppItem?
    fun getAppsByCategory(category: AppCategory): List<AppItem>
    fun getCategorySummaries(): List<CategorySummary>
}
