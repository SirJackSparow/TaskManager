package com.example.feature.presentation.tasklist

import com.example.core.domain.model.TaskEntity

sealed class ListTaskState {
    object Loading : ListTaskState()
    data class Success(val taskList: List<TaskEntity>) : ListTaskState()
    data class Error(val message: String) : ListTaskState()
}