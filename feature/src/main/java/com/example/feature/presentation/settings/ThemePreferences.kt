package com.example.feature.presentation.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = stringPreferencesKey("theme_mode")

    val themeMode: Flow<String> = dataStore.data
        .map { preferences -> preferences[THEME_KEY] ?: "light" }

    suspend fun setTheme(mode: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = mode
        }
    }
}
