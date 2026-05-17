package ru.rustore.mvp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rustore.mvp.di.AppContainer

class ProfileViewModel(
    private val container: AppContainer,
) : ViewModel() {

    fun resetOnboarding(onDone: () -> Unit) {
        viewModelScope.launch {
            container.onboardingPreferences.setOnboardingCompleted(false)
            onDone()
        }
    }

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProfileViewModel(container) as T
                }
            }
    }
}
