package ru.rustore.mvp.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.rustore.mvp.di.AppContainer
import ru.rustore.mvp.ui.navigation.Routes

class OnboardingViewModel(
    private val container: AppContainer,
    private val navController: NavController,
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            container.onboardingPreferences.setOnboardingCompleted(true)
            navController.navigate(Routes.STORE) {
                popUpTo(Routes.ONBOARDING) { inclusive = true }
            }
        }
    }

    companion object {
        fun factory(
            container: AppContainer,
            navController: NavController,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OnboardingViewModel(container, navController) as T
            }
        }
    }
}
