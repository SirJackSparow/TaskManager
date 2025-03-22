package com.example.core.domain.usecases

import com.example.core.data.repositories.TaskRepository
import com.example.core.domain.model.TaskEntity
import javax.inject.Inject

class InsertTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: TaskEntity) = taskRepository.insertTask(task)
}