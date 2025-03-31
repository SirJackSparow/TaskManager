package com.example.feature.presentation.securescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureViewModel @Inject constructor(
    private val securePreferences: SecurePreferences
) : ViewModel() {

    private val _state : MutableStateFlow<String?> = MutableStateFlow(null)
    val state = _state

    fun getSecureData() {
        viewModelScope.launch {
            securePreferences.getMasterKey().collect {
                _state.value = it
            }
        }
    }

    fun saveSecureData(value: String) {
        viewModelScope.launch {
            securePreferences.saveMasterKey(value)
        }
    }
}