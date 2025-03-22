package com.example.feature.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val themePreferences: ThemePreferences) : ViewModel() {

    private val _themeMode = MutableStateFlow("light")
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    init {
        themePreferences.themeMode.onEach { mode ->
            _themeMode.value = mode
        }.launchIn(viewModelScope)
    }

    fun setTheme(mode: String) {
        viewModelScope.launch {
            themePreferences.setTheme(mode)
        }
    }
}
