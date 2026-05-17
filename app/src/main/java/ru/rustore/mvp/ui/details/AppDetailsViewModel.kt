package ru.rustore.mvp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.repository.AppInstallRepository
import ru.rustore.mvp.data.repository.InstallState
import ru.rustore.mvp.di.AppContainer

data class AppDetailsUiState(
    val app: AppItem? = null,
    val installState: InstallState = InstallState.NotInstalled,
)

sealed interface AppDetailsEvent {
    data class OpenInstalledApp(val appId: String) : AppDetailsEvent
}

class AppDetailsViewModel(
    container: AppContainer,
    private val appId: String,
) : ViewModel() {

    private val installRepository: AppInstallRepository = container.installRepository
    private val app: AppItem? = container.appRepository.getAppById(appId)

    private val _uiState = MutableStateFlow(
        AppDetailsUiState(app = app, installState = installRepository.stateOf(appId)),
    )
    val uiState: StateFlow<AppDetailsUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(Channel.BUFFERED)
    val events: Flow<AppDetailsEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            installRepository.states.collect { states ->
                _uiState.value = AppDetailsUiState(
                    app = app,
                    installState = states[appId] ?: InstallState.NotInstalled,
                )
            }
        }
    }

    fun install() {
        if (installRepository.stateOf(appId) !is InstallState.NotInstalled) return
        installRepository.startInstall(appId)
        viewModelScope.launch {
            installRepository.states
                .map { it[appId] }
                .first { it == InstallState.Installed }
            _events.trySend(AppDetailsEvent.OpenInstalledApp(appId))
        }
    }

    fun uninstall() {
        installRepository.uninstall(appId)
    }

    fun openInstalledApp() {
        if (installRepository.stateOf(appId) == InstallState.Installed) {
            _events.trySend(AppDetailsEvent.OpenInstalledApp(appId))
        }
    }

    companion object {
        fun factory(container: AppContainer, appId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppDetailsViewModel(container, appId) as T
                }
            }
    }
}
