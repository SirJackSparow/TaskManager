package com.example.feature.presentation.detailstask

import com.example.core.domain.model.TaskEntity

sealed class DetailState {
    object Loading : DetailState()
    data class SuccessUpdated(val message: String) : DetailState()
    data class LoadData(val task: TaskEntity) : DetailState()
    data class Error(val message: String) : DetailState()
}