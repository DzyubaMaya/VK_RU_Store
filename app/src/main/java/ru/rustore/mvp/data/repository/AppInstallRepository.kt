package ru.rustore.mvp.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Float) : InstallState
    data object Installed : InstallState
}

class AppInstallRepository(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) {
    private val _states = MutableStateFlow<Map<String, InstallState>>(emptyMap())
    val states: StateFlow<Map<String, InstallState>> = _states.asStateFlow()

    fun observeState(appId: String): StateFlow<InstallState> {
        val initial = _states.value[appId] ?: InstallState.NotInstalled
        val flow = MutableStateFlow(initial)
        scope.launch {
            _states.map { it[appId] ?: InstallState.NotInstalled }.collect { flow.value = it }
        }
        return flow.asStateFlow()
    }

    fun stateOf(appId: String): InstallState =
        _states.value[appId] ?: InstallState.NotInstalled

    fun startInstall(appId: String) {
        if (stateOf(appId) is InstallState.Installing) return
        if (stateOf(appId) == InstallState.Installed) return
        scope.launch {
            val steps = 20
            for (i in 1..steps) {
                _states.update { it + (appId to InstallState.Installing(i / steps.toFloat())) }
                delay(100)
            }
            _states.update { it + (appId to InstallState.Installed) }
        }
    }

    fun uninstall(appId: String) {
        _states.update { it + (appId to InstallState.NotInstalled) }
    }
}
