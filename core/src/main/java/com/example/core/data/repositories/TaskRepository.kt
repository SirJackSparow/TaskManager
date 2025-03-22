package com.example.core.data.repositories

import com.example.core.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<TaskEntity>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun deleteTask(id: Int)
    suspend fun updateTaskStatus(id: Int, isCompleted: Boolean)
    suspend fun updateTasksOrder(tasks: List<TaskEntity>)
}
