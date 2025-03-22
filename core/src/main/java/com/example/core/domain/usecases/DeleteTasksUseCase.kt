package com.example.core.domain.usecases

import com.example.core.data.repositories.TaskRepository
import javax.inject.Inject

class DeleteTasksUseCase @Inject constructor(private  val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: Int) = taskRepository.deleteTask(taskId)
}