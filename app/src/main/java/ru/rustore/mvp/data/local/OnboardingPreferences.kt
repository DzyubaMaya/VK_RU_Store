package ru.rustore.mvp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "onboarding_prefs",
)

class OnboardingPreferences(
    private val context: Context,
) {
    val onboardingCompleted: Flow<Boolean> =
        context.onboardingDataStore.data.map { prefs ->
            prefs[KEY_COMPLETED] ?: false
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[KEY_COMPLETED] = completed
        }
    }

    private companion object {
        val KEY_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }
}
