package com.example.core.domain.usecases

import com.example.core.common.Resource
import com.example.core.data.repositories.TaskRepository
import com.example.core.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<Resource<List<TaskEntity>>> = flow {
        emit(Resource.Loading)
        repository.getTasks()
            .catch { emit(Resource.Error("Failed to load tasks")) }
            .collect { tasks -> emit(Resource.Success(tasks)) }
    }
}
