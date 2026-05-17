package ru.rustore.mvp.ui.screenshots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.di.AppContainer

data class ScreenshotsUiState(
    val app: AppItem? = null,
    val startIndex: Int = 0,
)

class ScreenshotsViewModel(
    container: AppContainer,
    appId: String,
    startIndex: Int,
) : ViewModel() {

    val uiState: ScreenshotsUiState = ScreenshotsUiState(
        app = container.appRepository.getAppById(appId),
        startIndex = startIndex.coerceAtLeast(0),
    )

    companion object {
        fun factory(
            container: AppContainer,
            appId: String,
            startIndex: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ScreenshotsViewModel(container, appId, startIndex) as T
            }
        }
    }
}
