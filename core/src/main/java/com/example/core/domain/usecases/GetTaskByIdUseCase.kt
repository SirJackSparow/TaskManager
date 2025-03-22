package com.example.core.domain.usecases

import com.example.core.data.repositories.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Int) = repository.getTaskById(taskId)
}