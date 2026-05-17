package ru.rustore.mvp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.rustore.mvp.di.AppContainer
import ru.rustore.mvp.ui.navigation.Routes

data class MainUiState(
    val isReady: Boolean = false,
    val startDestination: String = Routes.ONBOARDING,
)

class MainViewModel(
    container: AppContainer,
) : ViewModel() {

    val uiState: StateFlow<MainUiState> =
        container.onboardingPreferences.onboardingCompleted
            .map { completed ->
                MainUiState(
                    isReady = true,
                    startDestination = if (completed) Routes.STORE else Routes.ONBOARDING,
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState(isReady = false),
            )

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(container) as T
                }
            }
    }
}
