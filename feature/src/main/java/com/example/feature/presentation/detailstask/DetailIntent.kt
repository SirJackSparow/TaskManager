package com.example.feature.presentation.detailstask

sealed class DetailIntent {
    data class UpdateData(val id: Int, val isCompleted: Boolean, val progress: Int) : DetailIntent()
    data class LoadData(val taskId: Int) : DetailIntent()
}