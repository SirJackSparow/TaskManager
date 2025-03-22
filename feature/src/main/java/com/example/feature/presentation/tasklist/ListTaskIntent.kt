package com.example.feature.presentation.tasklist

import com.example.core.domain.model.TaskEntity

sealed class ListTaskIntent {
    object LoadTasks : ListTaskIntent()
    data class DeleteTask(val taskId: Int) : ListTaskIntent()
    data class ReorderTasks(val tasks: List<TaskEntity>) : ListTaskIntent()
}
