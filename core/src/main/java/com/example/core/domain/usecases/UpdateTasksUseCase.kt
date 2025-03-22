package com.example.core.domain.usecases

import com.example.core.data.repositories.TaskRepository
import javax.inject.Inject

class UpdateTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int, isCompleted: Boolean) =
        repository.updateTaskStatus(id, isCompleted)
}