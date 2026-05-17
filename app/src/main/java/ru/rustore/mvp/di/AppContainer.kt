package ru.rustore.mvp.di

import android.content.Context
import ru.rustore.mvp.data.local.OnboardingPreferences
import ru.rustore.mvp.data.repository.AppInstallRepository
import ru.rustore.mvp.data.repository.AppRepository
import ru.rustore.mvp.data.repository.FakeAppRepository

class AppContainer(context: Context) {
    val appRepository: AppRepository = FakeAppRepository()
    val onboardingPreferences = OnboardingPreferences(context.applicationContext)
    val installRepository = AppInstallRepository()
}
