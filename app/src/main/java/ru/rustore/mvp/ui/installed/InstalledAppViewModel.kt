package ru.rustore.mvp.ui.installed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.di.AppContainer

data class InstalledAppUiState(
    val app: AppItem? = null,
)

class InstalledAppViewModel(
    container: AppContainer,
    appId: String,
) : ViewModel() {

    val uiState: InstalledAppUiState = InstalledAppUiState(
        app = container.appRepository.getAppById(appId),
    )

    companion object {
        fun factory(container: AppContainer, appId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return InstalledAppViewModel(container, appId) as T
                }
            }
    }
}
