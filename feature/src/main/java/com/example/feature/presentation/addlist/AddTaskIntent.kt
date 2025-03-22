package com.example.feature.presentation.addlist

import com.example.core.domain.model.TaskEntity

sealed class AddTaskIntent {
    data class AddData(val data: TaskEntity): AddTaskIntent()
}