package com.example.core.data.repositories

import com.example.core.data.dao.TaskDao
import com.example.core.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<TaskEntity>> = taskDao.getTasks()

    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    override suspend fun deleteTask(id: Int) = taskDao.deleteTask(id)

    override suspend fun updateTaskStatus(id: Int, isCompleted: Boolean) =
        taskDao.updateTaskStatus(id, isCompleted)

    override suspend fun updateTasksOrder(tasks: List<TaskEntity>) {
        taskDao.updateTasks(tasks)
    }
}