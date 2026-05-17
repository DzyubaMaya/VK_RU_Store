package ru.rustore.mvp.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.rustore.mvp.data.model.AppCategory
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.model.CategorySummary
import ru.rustore.mvp.data.repository.AppRepository
import ru.rustore.mvp.di.AppContainer

data class StoreUiState(
    val apps: List<AppItem> = emptyList(),
    val categorySummaries: List<CategorySummary> = emptyList(),
    val selectedCategory: AppCategory? = null,
    val searchQuery: String = "",
    val isCategorySheetVisible: Boolean = false,
)

class StoreViewModel(
    container: AppContainer,
) : ViewModel() {

    private val repository: AppRepository = container.appRepository

    private val _uiState = MutableStateFlow(
        buildState(selectedCategory = null, searchQuery = "", isCategorySheetVisible = false),
    )
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    fun openCategorySheet() {
        _uiState.update { it.copy(isCategorySheetVisible = true) }
    }

    fun dismissCategorySheet() {
        _uiState.update { it.copy(isCategorySheetVisible = false) }
    }

    fun selectCategory(category: AppCategory?) {
        val current = _uiState.value
        _uiState.value = buildState(
            selectedCategory = category,
            searchQuery = current.searchQuery,
            isCategorySheetVisible = false,
        )
    }

    fun clearFilter() {
        val current = _uiState.value
        _uiState.value = buildState(
            selectedCategory = null,
            searchQuery = current.searchQuery,
            isCategorySheetVisible = false,
        )
    }

    fun setSearchQuery(query: String) {
        val current = _uiState.value
        _uiState.value = buildState(
            selectedCategory = current.selectedCategory,
            searchQuery = query,
            isCategorySheetVisible = current.isCategorySheetVisible,
        )
    }

    fun clearSearch() {
        setSearchQuery("")
    }

    private fun buildState(
        selectedCategory: AppCategory?,
        searchQuery: String,
        isCategorySheetVisible: Boolean,
    ): StoreUiState {
        val base = if (selectedCategory == null) {
            repository.getApps()
        } else {
            repository.getAppsByCategory(selectedCategory)
        }
        val q = searchQuery.trim()
        val filtered = if (q.isBlank()) base else base.filter { app ->
            app.title.contains(q, ignoreCase = true) ||
                app.developer.contains(q, ignoreCase = true)
        }
        return StoreUiState(
            apps = filtered,
            categorySummaries = repository.getCategorySummaries(),
            selectedCategory = selectedCategory,
            searchQuery = searchQuery,
            isCategorySheetVisible = isCategorySheetVisible,
        )
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return StoreViewModel(container) as T
                }
            }
    }
}
